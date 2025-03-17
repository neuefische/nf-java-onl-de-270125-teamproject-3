import { Movie } from "./MainLayout.tsx";
import { useLocation, useNavigate, useParams, Link } from "react-router";
import { ChangeEvent, FormEvent, useEffect, useState } from "react";
import { Box, Button, TextField, CircularProgress } from "@mui/material";
import axios from "axios";

export default function EditMovieForm() {
    const navigate = useNavigate();
    const location = useLocation();
    const { id } = useParams<{ id: string }>();
    const baseURL = "/api/movie";

    const [updatingMovie, setUpdatingMovie] = useState<Movie | null>(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const getUpdatingMovie = (id: string) => {
        setLoading(true);
        axios
            .get(`${baseURL}/${id}`)
            .then((response) => {
                setUpdatingMovie(response.data);
                setLoading(false);
            })
            .catch((error) => {
                setError(error.message);
                setLoading(false);
            });
    };

    useEffect(() => {
     // without the first if clause for id the linter would complain inside that id could be undefined
        if (id) {
            if (location.state && (location.state as Movie).id === id) {
                setUpdatingMovie(location.state as Movie);
            } else {
                getUpdatingMovie(id);
            }
        }
    }, [id, location.state]);

    const handleSaveMovie = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        setLoading(true);

        axios.put(`${baseURL}/${id}`, updatingMovie)
            .then((response) => {
                if (response.status >= 200 && response.status < 300) {
                    console.log('PUT request successful:', response.data);
                    navigate(`/${updatingMovie?.id}`, { state: updatingMovie });
                } else {
                    console.error('PUT request failed with status:', response.status);
                    console.error('Response data:', response.data);
                }
                setLoading(false);
            })
            .catch((error) => {
                setError(error.message);
                setLoading(false);
            });
    };

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        if (updatingMovie) {
            setUpdatingMovie({ ...updatingMovie, [event.target.name]: event.target.value });
        }
    };

    const handleCancelClick = () => {
        if (updatingMovie) {
            navigate(`/${updatingMovie.id}`, { state: updatingMovie });
        }
    };

    if (loading) {
        return (
            <div>
                <CircularProgress />
            </div>
        );
    }

    if (error) {
        return (
            <div>
                <h2>Error</h2>
                <p>{error}</p>
                <Link to={"/"}>Back to main page</Link>

            </div>
        );
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
                    value={updatingMovie?.title || ""}
                    onChange={handleChange}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="director"
                    label="Director"
                    name="director"
                    value={updatingMovie?.director || ""}
                    onChange={handleChange}
                />
                <TextField
                    margin="normal"
                    required
                    fullWidth
                    id="releaseYear"
                    label="Release Year"
                    name="releaseYear"
                    value={updatingMovie?.releaseYear || ""}
                    onChange={handleChange}
                />
                <Button type="submit"
                        variant="contained"
                        sx={{ mt: 3, mb: 2, mr: 2 }}
                        disabled={loading}>
                    Save
                </Button>
                <Button onClick={handleCancelClick}
                        variant="contained"
                        sx={{ mt: 3, mb: 2 }}>
                    Cancel
                </Button>
            </Box>
        </div>
    );
}
