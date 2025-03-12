import {useEffect, useState} from "react";
import axios from "axios";
import './App.css';

interface Movie {
    id: string;
    title: string;
    director: string;
    releaseYear: number;
}

function App() {

    const [movies, setMovies] = useState<Movie[]>([]);
    const [movie, setMovie] = useState<Movie>();

    const baseURL = "/api/movie"

    const getMovies = () => {
        console.log("Fetching Movies...")

        axios.get(baseURL)
            .then((response) => {
                console.log("Request finished")
                console.log(response.data)
                setMovies(response.data)
            })
            .catch((errorResponse) => {
                console.log(errorResponse)
            })

        console.log("Movies fetched successfully!")
    }

    const getMovie = (id:string) => {
        console.log(`Fetching Movie with ${id}...`)

        axios.get(`${baseURL}/${id}`)
            .then((response) => {
                console.log("Request finished")
                console.log(response.data)
                setMovie(response.data)
            })
            .catch((errorResponse) => {
                console.log(errorResponse)
            })

        console.log("Movie fetched successfully!")
    }

    useEffect(() => {
        getMovies();
        getMovie("67d1702820ade63ce409e544")
    }, [])

  return (
    movies.map((movie) => <div key={movie.id}>
        <h3>{movie.title}</h3>
        <p>{movie.director}</p>
        <p>{movie.releaseYear}</p>
    </div>)
  )
}

export default App
