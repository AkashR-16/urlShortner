import axios from "axios";
import { useEffect, useState } from "react";
import CopyToClipboard from "react-copy-to-clipboard";

const LinkResult =({inputValue}) => { 
 
    const backendUrl = "http://127.0.0.1:9001/"

    // BASE URL (backend)
    const api = axios.create({
      baseURL: backendUrl
    })
    const[shortenLink, setShortenLink]= useState ("");
    console.log(shortenLink);
    const[copied, setCopied]= useState (false);
    const [loading,setLoading]= useState(false);
    const [error,setError]= useState(false);
    const fetchData =async () => {
        try{
            setLoading(true);
            //const respose = await axios(`https://api.shrtco.de/v2/shorten?url=${inputValue}`);
            //setShortenLink(respose.data.result.full_short_link);
            const data = {
                short_url: "",
                full_url: inputValue
                };
            //const respose = await axios(`https://api.shrtco.de/v2/shorten?url=${inputValue}`);
            const respose = await api.post("/shortenurl", data,{'Content-Type': 'application/json'});
            console.log('fullurl='+respose.data.full_url); 
            console.log('shorturl='+respose.data.short_url); 
            setShortenLink(respose.data.short_url);

        }catch(err){
            setError(err)

        }finally{
            setLoading(false);
        }
    }
    useEffect(() =>{
        if(inputValue.length){
            fetchData();
        }
    },[inputValue]);


    useEffect(() =>{
        const timer =setTimeout(() =>{
            setCopied(false);
        },1000);  
        return () => clearTimeout(timer);
    },[copied]);
   
    if(loading){
        return<p className="noData">Loading...</p>
    }

    if(error){
        return<p className="noData">Something went wrong...</p>
    }

    return(
        <>
        {shortenLink && (
        <div className="result">
        <p>{shortenLink}</p>
         <CopyToClipboard text={shortenLink}
         onCopy={() => setCopied(true)}
         >
         <button className={copied ? "copied" : ""}>Copy to Clipboard</button>
         </CopyToClipboard>
     </div>

        )}
        
        </>
    )
}
export default LinkResult;