import { useState } from 'react';
import Plus from '../../assets/icons/Plus.svg';
import Minus from '../../assets/icons/Minus.svg';
import './RutaVehicle.css';

export const RutaVehicle = ({ Vehicle, Comandes }) => {
    const[show, setShow] = useState(false);

    return(
        <div className="rutaVehicleContainer">
            <div className="vehicle">
                { Vehicle }
                <input type="image" alt="plus/minus" src={show === true ? Minus : Plus} id="saveForm" onClick={() => setShow(!show)}/>
            </div>
            {show && <div className="comandes">
                { Comandes }
            </div>}
        </div>
    )
}



