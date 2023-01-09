import { IconText } from '../index';
import PropTypes from 'prop-types';
import Boxes from '../../assets/images/Caixes.svg';
import Location from '../../assets/icons/Ubicacio.svg';
import Box from '../../assets/icons/Caixa.svg';
import Plus from '../../assets/icons/Plus.svg';
import Minus from '../../assets/icons/Minus.svg';
import { useState } from 'react';
import './Comanda.css';
import { Product } from '../../components/index';

function CheckBox (props) {
    const [checked, setChecked] = useState(false);

    const handleChange = () => { 
        setChecked(!checked); 
        //console.log(props);
        if(!checked) {
            props.vec.push(
                {
                    id: props.comandaId, 
                    restaurant: props.comandaName, 
                    numPackages: props.capacity, 
                    productes: props.prods,
                    location: props.address, 
                    price: props.price,
                    userMail: props.mail,
                    estate: "Created"
                }
            )
        }
        else {
            var index = 0;
            for(var i = 0; i < props.vec.length; ++i) {
                if (props.vec[i].id === props.comandaId) {
                    index = i;
                    break;
                }
            }
            props.vec.splice(index,1);
        }
    }; 
    return (
        <div>
            <input className="checkBox" type="checkbox" value="" 
            id="flexCheckDefault" onChange={handleChange}/>
        </div>
    )
}

export function Convierte_coordenada(coord) {
    var str = '';
    for(var i = 0; i < coord.length/2; ++i) {
        str = str + coord[i];
    }
    return str;
}

export const Comanda = ({ comandaName, address, bultos, comandaId, vec, vec_productes, checkBoxEnabled, mail, price }) => {
    const capacitat = 'Bultos: ' + bultos;
    var lon = Convierte_coordenada(address.lon.toString());
    var lat = Convierte_coordenada(address.lat.toString());
    const direccio = 'Direcció: ' + lon + ', ' + lat;
    const[show, setShow] = useState(false);
    const prods = [];
    for(var i = 0; i < vec_productes.length; i++) {
        prods.push(
            <Product 
                productName = {vec_productes[i].name} 
                quantity = {vec_productes[i].quantity} 
                ocupation = {vec_productes[i].size} 
            />
        );
    }

    return(
        <div className='comandaGeneralContainer'>
            <div className="comandaContainer">
                <div>
                    {checkBoxEnabled 
                    && 
                    <CheckBox 
                        comandaId = {comandaId} 
                        vec = {vec} 
                        capacity = {bultos} 
                        comandaName = {comandaName} 
                        address = {address} 
                        prods = {vec_productes}
                        mail = {mail}
                        price = {price}
                    />}
                </div>
                <div className="imageContainer2">
                    <img src={Boxes} alt="Boxes"/>    
                </div>
                <div className="comandaInformation">
                    <div className="comandaIdentifier">
                        <p className="comandaName">{ comandaName }</p>
                        
                    </div>
                    <IconText icon={Location} text={direccio} />
                    <IconText icon={Box} text={capacitat}/>
                </div>
                
                <input type="image" alt="plus/minus" src={show === true ? Minus : Plus} id="saveForm" onClick={() => setShow(!show)}/>
            </div>
            <div className='productesDeComandaContainer'>
                {show && <div className="productes">
                    { prods }
                </div>}
            </div>
        </div>
    )
}

Comanda.propTypes = {
    comandaName: PropTypes.string.isRequired,
    //address: PropTypes.string.isRequired, 
    bultos: PropTypes.number.isRequired,
    comandaId: PropTypes.string.isRequired,
    checkBoxEnabled: PropTypes.bool.isRequired,
}