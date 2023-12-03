import React, { useState, useEffect } from 'react';
import WebPlayback from './WebPlayback'
import Login from './Login'
import './App.css';

function App() {

    const [token, setToken] = useState('');
    const [song, setSong] = useState('');
    useEffect(() => {
        console.log("making request")
        async function getToken() {
            const response = await fetch('http://localhost:8000/getAccessToken/', {
                method: "GET",
                mode: "cors",
                headers: {'Content-Type': 'application/json'}

            });
            if (!response.ok) {
                const errorMessage = await response.text();
                console.error('Spotify API Error:', errorMessage);
            } else {
                const json = await response.json();
                console.log(json);
                setToken(json.access_token);
                setSong(json.currentSong);
            }
        }

        getToken();

    }, []);

    return (
        <>
            { (token === '') ? <Login/> : <WebPlayback token={token} song={song} /> }
        </>
    );
}

export default App;
