import './IconText.css';

export const IconText = ({ icon, text, val }) => {
    if (val === undefined) val = ''
    return(
        <div className="iconText">
            <img src={icon} alt="Icon"/>
            <p className="boldText">{ text + val }</p>
        </div>
    )
}