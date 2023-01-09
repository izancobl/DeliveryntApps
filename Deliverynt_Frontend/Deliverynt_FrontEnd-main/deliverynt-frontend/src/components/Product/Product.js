import { IconText } from '../index';
import Groceries from '../../assets/images/Groceries.svg';
//import Drinks from '../../assets/images/Drinks.svg';
import BoxIcon from '../../assets/icons/Caixa.svg';
import PropTypes from 'prop-types';
import './Product.css';

export const Product = ({ productName, quantity, ocupation }) => {
    const quantitat = 'x' + quantity;
    const ocupacio = 'Ocupació: ' + ocupation;
    return(
        <div className="productContainer">
            <div className="imageContainer2">
                <img src={Groceries} alt="Groceries"/>    
            </div>
            <div className="productInformation">
                <div className="productIdentifier">
                    <p className="productName">{ productName }</p>
                    <p>{ quantitat }</p>
                </div>
                <IconText icon={ BoxIcon } text={ ocupacio } />
            </div>
        </div>
    )
}

Product.propTypes = {
    productName: PropTypes.string.isRequired,
    quantity: PropTypes.number.isRequired,
    ocupation: PropTypes.number.isRequired,
}