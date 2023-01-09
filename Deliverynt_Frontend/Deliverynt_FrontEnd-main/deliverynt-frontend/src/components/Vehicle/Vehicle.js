import { IconText } from '../index';
import PropTypes from 'prop-types';
import RedTruck from '../../assets/images/CamióVermell.svg'
import BlueTruck from '../../assets/images/CamióBlau.svg'
import GreenTruck from '../../assets/images/CamióVerd.svg'
import BatteryLowIcon from '../../assets/icons/BateriaBaixa.svg'
import Battery33 from '../../assets/icons/BateriaCarregant33.svg'
import BoxIcon from '../../assets/icons/Caixa.svg'
import './Vehicle.css';
import {useState} from 'react'


function CheckBox (props) { //{id, v, c}
    const [checked, setChecked] = useState(false);
    //por aqui dentro tiene que haber un metodo para meter lo de la suma
    const handleChange = () => { 
        setChecked(!checked); 
        {
            if(!checked) {
                props.vec.push(
                    {
                        numberPlate: props.vehicleId,
                        battery: props.battery, 
                        capacity: props.capacity
                    }
                )
            }
            else {
                var index = 0
                for(var i = 0; i < props.vec.length; ++i) {
                    if (props.vec[i].numberPlate === props.vehicleId) {
                        index = i
                        break
                    }
                }
                props.vec.splice(index,1)
                //console.log(props.vec)
            }
        }
    }; 
    return (
        <div>
            <input className="checkBox" type="checkbox" value="" 
            id="flexCheckDefault" onChange={handleChange}/>
        </div>
    )
}

function Color_Camio ({cap}) {
    if (cap >= 200) {
        return(<img src={RedTruck} alt="Red truck"/>)
    } else if (cap >= 100) {
        return(<img src={BlueTruck} alt="Blue truck"/>)
    } else {
        return(<img src={GreenTruck} alt="Green truck"/>)
    }
}

function Color_bateria ({autono, autonomi}) {
    if (autono < 33) {
        return(<IconText icon={BatteryLowIcon} text={autonomi}/>) 
    } else {
        return(<IconText icon={Battery33} text={autonomi}/>) 
    }
}

export const Vehicle = ({vehicleName, battery, capacity, vehicleId, vec, checkBoxEnabled}) => {
    const autonomia = 'Autonomia: ' + battery;
    const capacitat = 'Capacitat: ' + capacity;
    //const a = <Color_bateria autono = {autonomy}/>;
    //console.log(props.vehicleId)
    return(
        <div className="vehicleContainer">
            <div>
                { checkBoxEnabled && <CheckBox vehicleName = {vehicleName} battery = {battery} capacity = {capacity} vehicleId = {vehicleId} vec = {vec}/> }
            </div>
            <div>
                <div className="imageContainer">
                    <Color_Camio cap = {capacity}/>
                </div>
            </div> 
            <div className="vehicleInformation">
                <div className="vehicleIdentifier">
                    <p className="vehicleName">{vehicleName}</p>
                    <p>{ ' | ' + vehicleId }</p>
                </div>
                <Color_bateria autono = {battery} autonomi = {autonomia}/>
                <IconText icon={BoxIcon} text={capacitat}/>
            </div>
        </div>
    )
}


Vehicle.propTypes = {
    vehicleName: PropTypes.string.isRequired,
    battery: PropTypes.number.isRequired, 
    capacity: PropTypes.number.isRequired,
    vehicleId: PropTypes.string.isRequired,
    checkBoxEnabled: PropTypes.bool.isRequired,
}
