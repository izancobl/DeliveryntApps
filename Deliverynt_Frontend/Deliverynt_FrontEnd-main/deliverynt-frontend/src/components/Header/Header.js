import Logo from '../../assets/images/LogoBlanc.svg'
import './Header.css'

function Header({title}) {
    return(
        <header className='header'>
            <img src={Logo} alt="logo" />
            <b>{title}</b>
        </header>
    )
}

export default Header;