
import { useState } from 'react';
import './App.css';
import BackgroundAnimation from './BackgroundAnimation';
import InputShortener from './InputShortener';
import LinkResult from  './LinkResult'; 

function App() {

  const [inputValue,setInputValue] = useState("");

  return (
    <div className="container">
      <InputShortener setInputValue ={setInputValue}/>
      <BackgroundAnimation/>
      <LinkResult inputValue={inputValue}/>
    </div>
  );
}

export default App;
