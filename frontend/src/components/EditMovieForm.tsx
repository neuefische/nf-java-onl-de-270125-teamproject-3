import {Movie} from "./MainLayout.tsx";
import {useLocation, useNavigate, useParams} from "react-router";
import {ChangeEvent, FormEvent, useState} from "react";
import {Box, Button, TextField} from "@mui/material";
import axios from "axios";

export default function EditMovieForm() {
    const navigate = useNavigate();
    const location = useLocation();

    const [updatingMovie, setUpdatingMovie] = useState<Movie>(location.state);

    const { id } = useParams<{ id: string }>();
    const baseURL = "/api/movie";

    const handleSaveMovie = (event: FormEvent<HTMLFormElement> ) => {
        event.preventDefault();

        axios.put(`${baseURL}/${id}`, updatingMovie)
            .then(
                response => {
                    // Handle successful response (status code 200-299)
                    if (response.status >= 200 && response.status < 300) {
                        // Process the response data
                        console.log('PUT request successful:', response.data);

                        navigate(`/${updatingMovie.id}`)

                    } else {
                        console.error('PUT request failed with status:', response.status);
                        console.error('Response data:', response.data); // Log response data
                    }
                }
            )
            .catch(
                error => console.error("Error setting up the request:", error.message)
            )



    };

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        setUpdatingMovie({...updatingMovie, [event.target.name]: event.target.value});
    }

    return (
        <div>
            <h2>Edit the movie entry</h2>
            <Box component="form" onSubmit={handleSaveMovie} noValidate sx={{ mt: 1 }}>
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="title"
                    label="Title"
                    name="title"
                    value={updatingMovie.title}
                    onChange={handleChange}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="director"
                    label="Director"
                    name="director"
                    value={updatingMovie.director}
                    onChange={handleChange}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="releaseYear"
                    label="Release Year"
                    name="releaseYear"
                    value={updatingMovie.releaseYear}
                    onChange={handleChange}
                />
                <Button type="submit"
                        variant="contained"
                        sx={{ mt: 3, mb: 2, mr: 2}}>
                    Save
                </Button>
                <Button onClick={() => navigate(`/${updatingMovie.id}`)}
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}>Cancel</Button>
            </Box>

        </div>
    )

}
