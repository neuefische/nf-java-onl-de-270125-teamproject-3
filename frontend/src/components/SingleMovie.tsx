import {useParams} from "react-router";
import {useEffect, useState} from "react";
import axios from "axios";

interface Movie {
    id: string;
    title: string;
    director: string;
    releaseYear: number;
}

export const SingleMovie = () => {
    const [movie, setMovie] = useState<Movie>();
    const {id} = useParams();
    const baseURL = "/api/movie"
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
        if(id) {
            getMovie(id);
        }
    }, [])

    return <div>
        <p>{movie?.id}</p>
        <p>{movie?.title}</p>
        <p>{movie?.director}</p>
        <p>{movie?.releaseYear}</p>
    </div>
}