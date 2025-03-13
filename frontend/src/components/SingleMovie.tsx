import { useParams } from "react-router";
import { useEffect, useState } from "react";
import axios from "axios";
import { Box, Typography, Container } from "@mui/material";

interface Movie {
    id: string;
    title: string;
    director: string;
    releaseYear: number;
    image: string;
}

export const SingleMovie = () => {
    const [movie, setMovie] = useState<Movie | null>(null);
    const { id } = useParams();
    const baseURL = "/api/movie";

    const getMovie = (id: string) => {
        console.log(`Fetching Movie with ${id}...`);

        axios
            .get(`${baseURL}/${id}`)
            .then((response) => {
                console.log("Request finished");
                console.log(response.data);
                setMovie(response.data);
            })
            .catch((errorResponse) => {
                console.log(errorResponse);
            });

        console.log("Movie fetched successfully!");
    };

    useEffect(() => {
        if (id) {
            getMovie(id);
        }
    }, [id]);

    if (!movie) {
        return <div>Loading...</div>;
    }

    return (
        <Container sx={{ textAlign: "center", marginTop: "32px" }}>
            {/* Title at the Top */}
            <Typography variant="h3" gutterBottom>
                {movie.title}
            </Typography>

            {/* Image Below Title */}
            <Box
                component="img"
                src={movie.image}
                alt={movie.title}
                sx={{
                    width: "100%",
                    maxWidth: "600px",
                    height: "auto",
                    borderRadius: "8px",
                    margin: "16px 0",
                }}
            />

            {/* Movie Information in the Middle */}
            <Box sx={{ margin: "16px 0" }}>
                <Typography variant="h5" color="text.secondary">
                    Director: {movie.director}
                </Typography>
                <Typography variant="h5" color="text.secondary">
                    Release Year: {movie.releaseYear}
                </Typography>
            </Box>
        </Container>
    );
};