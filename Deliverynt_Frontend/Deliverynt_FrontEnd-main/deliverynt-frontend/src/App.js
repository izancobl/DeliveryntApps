import './App.css';
import { VehiclesComandes, ResumEntrega, MenuPrincipal } from './pages/index';
import * as React from 'react';
import {BrowserRouter,Routes,Route} from "react-router-dom";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route exact path="/" element={<MenuPrincipal/>}/>
        <Route exact path="/vehicles_comandes" element={<VehiclesComandes/>}/>
        <Route exact path="/resum_entrega"  element={<ResumEntrega/>}/>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
