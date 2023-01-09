import { Header, Vehicle, Comanda, RutaVehicle, Product } from '../../components/index';
import {useEffect, useState} from 'react';
import { Convierte_coordenada } from '../../components/Comanda/Comanda';
import { useNavigate } from 'react-router-dom';
import './ResumEntrega.css';
import { getData } from '../../buffer';

export const ResumEntrega = () => {
   const url_routes = 'https://deliveryntbackend-production-729e.up.railway.app/v1/routes'
   //console.log(getData())
   const [routesData, setRoutesData] = useState([])

   const getRoutes = async () => {
      const resp = await fetch(url_routes, {
         method: 'POST',
         headers: {
             'Content-Type': 'application/json',
             'Authorization': '4uuthsja6ytgsm93992020jjfjsmmandajjd3929kdksa44gj4g7n7gfdg5h4dd'
           },
           body: getData(),
      })
      const data = await resp.json()
      setRoutesData(data)
  }

  useEffect(() => {
      getRoutes();
   }, []);
   
   const navigate = useNavigate()
   
   const rutaVehicles = []

   if(routesData.length > 0) {
      var vehicle = null
      for(var i = 0; i < routesData.length; i++) {
         const comandes = []
         vehicle = <Vehicle 
                     key={routesData[i].vehicle.numberPlate} 
                     vehicleName={'Vehicle ' + (i+1).toString()} 
                     vehicleId = {routesData[i].vehicle.numberPlate} 
                     battery = {routesData[i].vehicle.battery} 
                     capacity = {routesData[i].vehicle.capacity} 
                     checkBoxEnabled={false}
                  />
         for(var j = 0; j < routesData[i].comandes.length; j++) {
            const vec_prods = []
            for(var k = 0; k < routesData[i].comandes[j].productes.length; ++k) {
               vec_prods.push(
                  {
                      id: routesData[i].comandes[j].productes[k].id, 
                      name: routesData[i].comandes[j].productes[k].name, 
                      size: routesData[i].comandes[j].productes[k].size,
                      price: routesData[i].comandes[j].productes[k].price,
                      quantity: routesData[i].comandes[j].productes[k].quantity, 
                      photo: routesData[i].comandes[j].productes[k].photo
                  }
              )
            }
            comandes.push(
               <Comanda 
                  key={routesData[i].comandes[j].id} 
                  comandaName={routesData[i].comandes[j].restaurant} 
                  address={routesData[i].comandes[j].location} 
                  bultos={routesData[i].comandes[j].numPackages} 
                  comandaId={routesData[i].comandes[j].id} 
                  vec_productes={vec_prods} 
                  checkBoxEnabled={false}
                  mail={routesData[i].comandes[j].userMail}
                  price={routesData[i].comandes[j].price}
               />
            )
         }
         rutaVehicles.push(<RutaVehicle Vehicle={ vehicle } Comandes={ comandes } />)
      }
   }

    return(
        <div className="generalResumEntregaContainer">  
            <Header title = {'Resum Entrega'}/>
            <div className="resumEntregaContainer">
               {rutaVehicles}
            </div>
            <div className="buttonContainer">
                    <button className='menu_button' onClick={() => navigate(-2)} > Crear Ruta </button>
            </div>
        </div>
    )
}