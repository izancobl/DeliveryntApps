import './MenuPrincipal.css';
import { Header } from '../../components/index';
import Map from '../../components/Map/Map'
import { useNavigate } from 'react-router-dom';
import {useEffect, useState} from "react";

function MenuPrincipal() {
    const navigate = useNavigate()

    const [data, setData] = useState([]);
    const getRouteData = async () => {
        const resp = await fetch("https://deliveryntbackend-production-729e.up.railway.app/v1/routes/map", {
            method: "GET",
            headers: {
                Authorization: "4uuthsja6ytgsm93992020jjfjsmmandajjd3929kdksa44gj4g7n7gfdg5h4dd"
            }
        })
        const d = await resp.json()
        setData(d)
    }
    useEffect(() => {
            getRouteData()
    }, []);
    if (data.length !== 0)
    {
        return (
            <div className="generalMenuPrincipalContainer">
                <Header title = 'Pantalla Principal'/>
                <div className="menuPrincipalContainer">
                    <Map routeData = {data}>
                    </Map>
                    <div className="buttonContainerMenuPrincipal">
                        <button className='menu_button' onClick={() => navigate('/vehicles_comandes')}> Nova Ruta
                        </button>
                    </div>
                </div>
            </div>
        )
    }
    else {
        return (
            <div className="generalMenuPrincipalContainer">
                <Header/>
                <div className="menuPrincipalContainer">
                    <div className="buttonContainerMenuPrincipal">
                        <button className='menu_button' onClick={() => navigate('/vehicles_comandes')}> Nova Ruta
                        </button>
                    </div>
                </div>
            </div>
        )
    }
}

export default MenuPrincipal