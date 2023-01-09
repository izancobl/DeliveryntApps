import './Button.css'
import { useNavigate } from 'react-router-dom';

export const Button = ({title, cap_cotxes, cap_bultos}) => {
    const handleClick = () => {
        if(cap_bultos !== undefined && cap_cotxes !== undefined){
            if(cap_cotxes >= cap_bultos && cap_bultos !== 0)
            {
                console.log('enviando datos')
            }
        }
    }
    return (
        <div>
            <button enabled="true" className='button' onClick={()=> {handleClick();}}>{title}</button>
        </div>
    )
}