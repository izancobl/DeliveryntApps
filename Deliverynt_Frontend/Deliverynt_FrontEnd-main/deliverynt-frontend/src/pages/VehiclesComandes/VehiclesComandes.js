import { Header, Vehicle, Comanda, IconText } from '../../components/index';
import Box from '../../assets/icons/Caixa.svg'
import './VehiclesComandes.css';
import {useEffect, useState} from 'react'
import { useNavigate } from 'react-router-dom';
import { setData } from '../../buffer';

let check_cotxes = []
let check_comandes = []

function Calcular_capacitat(v) {
    let sum = 0;
    for (var i = 0; i < v.length; ++i) {
        if(v[i].capacity !== undefined) sum += v[i].capacity 
        else sum += v[i].numPackages 
    }
    return sum;
}

function color_boton() {
    var a = Calcular_capacitat(check_comandes)
    var b = Calcular_capacitat(check_cotxes)
    if (a > b || (a === 0 || b === 0) || (a === undefined || b === undefined) ) {
        Array.from(document.querySelectorAll('.button')).map(function(button) {
            button.style.backgroundColor="darkgrey";
        })
    }
    else {
        Array.from(document.querySelectorAll('.button')).map(function(button) {
            button.style.backgroundColor="rgb(151, 39, 39, 1)";
        })
    }
}

function VehiclesComandes() {
    const url_cotxes = 'https://deliveryntbackend-production-729e.up.railway.app/v1/vehicles'
    const url_comandes = "https://deliveryntbackend-production-729e.up.railway.app/v1/comandes"

    const [suma_cotxes, setSumaCotxes] = useState(0);
    const cambio_vector_coches = () => {
        let x = Calcular_capacitat(check_cotxes)
        setSumaCotxes(0)
        if(x !== 0) setSumaCotxes(suma_cotxes + x)
    };
    const [suma_bultos, setSumaBultos] = useState(0);
    const cambio_vector_bultos = () => {
        let x = Calcular_capacitat(check_comandes)
        setSumaBultos(0)
        if(x !== 0) setSumaBultos(suma_bultos + x)  
    };

    const [cotxesData, setCotxesData] = useState([])

    const get_cotxes_back_end=async()=>{
        const resp = await fetch(url_cotxes, {
            method : "GET",
            headers: {
                Authorization: "4uuthsja6ytgsm93992020jjfjsmmandajjd3929kdksa44gj4g7n7gfdg5h4dd",
              },
        })
        const data = await resp.json()
        //console.log(resp)
        setCotxesData(data)
    }

    useEffect(() => {
        get_cotxes_back_end();
    }, []);

    const vec_cotxes = []
    var i
    for(i = 0; i < cotxesData.length; i++) {
        vec_cotxes.push(
            <Vehicle 
                key={cotxesData[i].numberPlate} 
                vehicleName={'Vehicle ' + (i+1).toString()}
                vehicleId = {cotxesData[i].numberPlate} 
                battery = {cotxesData[i].battery} 
                capacity = {cotxesData[i].capacity} 
                vec = {check_cotxes}
                checkBoxEnabled={ true }
            />
        )
    }

    const[bultosData, setBultosData] = useState([])

    const get_bultos_back_end=async()=>{
        const resp = await fetch(url_comandes, {headers: {
            'Authorization': '4uuthsja6ytgsm93992020jjfjsmmandajjd3929kdksa44gj4g7n7gfdg5h4dd'
          },})
        const data = await resp.json()
        setBultosData(data)
    }

    useEffect(() => {
        get_bultos_back_end();
    }, []);

    const vec_bultos = []
    //console.log(bultosData)
    for(i = 0; i < bultosData.length; i++) {
        //const prods = []
        const vec_prods = []
        for(var j = 0; j < bultosData[i].productes.length; ++j) {
            vec_prods.push(
                {
                    id: bultosData[i].productes[j].id, 
                    name: bultosData[i].productes[j].name, 
                    size: bultosData[i].productes[j].size,
                    price: bultosData[i].productes[j].price,
                    quantity: bultosData[i].productes[j].quantity, 
                    photo: bultosData[i].productes[j].photo
                }
            )
        }
        //console.log(bultosData)
        vec_bultos.push(
            <Comanda 
                key={i} 
                comandaName={bultosData[i].restaurant} 
                address={bultosData[i].location}
                bultos={bultosData[i].numPackages} 
                comandaId={bultosData[i].id}
                vec = {check_comandes} 
                vec_productes = { vec_prods }
                checkBoxEnabled={ true } 
                mail={bultosData[i].userMail}
                price={bultosData[i].price}
            />
        )
    }

    const navigate = useNavigate()
    const handleClick = () => {
        var x = Calcular_capacitat(check_cotxes) 
        var y = Calcular_capacitat(check_comandes)
        if(x >= y && y !== 0 && x !== 0) {
            const dades_entrega = JSON.stringify({"vehicles": check_cotxes, "orders": check_comandes});
            setData(dades_entrega)
            check_cotxes = []
            check_comandes = []
            navigate('/resum_entrega');
        }
    }
        
    return(
        <div className="generalVehiclesComandesContainer">  
            <Header title = {'Crear Entrega'}/>
            <div className="vehiclesComandesContainer">
                <div className="vehicles">
                    {vec_cotxes}
                </div>
                <div className="comandes">
                    {vec_bultos}
                </div>
            </div>
            <div className="selectionsContainer">
                <IconText icon={Box} text={'Capacitat seleccionada: ' } val = {Calcular_capacitat(check_cotxes)} />
                <IconText icon={Box} text= {'Bultos seleccionats: '} val = {Calcular_capacitat(check_comandes)} />
            </div>
            <div className='button-group'>
                <button onClick={() => {cambio_vector_coches(); color_boton();}} className='mini_button'> Calcula Cotxes </button>
                <button onClick={() => {cambio_vector_bultos(); color_boton()}} className='mini_button'> Calcula Bultos </button>
            </div>
            <div className="buttonContainer">
                <button onClick={handleClick} className='button' cap_cotxes = {Calcular_capacitat(check_cotxes)} cap_bultos = {Calcular_capacitat(check_comandes)} v_cotxes = {check_cotxes} v_comandes = {check_comandes} > Calcular Ruta </button>
            </div>
        </div>
    )
}

export default VehiclesComandes;