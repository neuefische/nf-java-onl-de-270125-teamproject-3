import React, { useState } from 'react';
import {
    TextField,
    Button,
    Container,
    Typography,
    Box,
} from '@mui/material';
import { useNavigate } from 'react-router';
import axios from 'axios';

export default function AddMovieForm() {
    const [title, setTitle] = useState('');
    const [director, setDirector] = useState('');
    const [releaseYear, setReleaseYear] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (event: React.FormEvent) => {
        event.preventDefault();
        try {
            const baseURL = "/api/movie";
            await axios.post(baseURL, { //POST request
                title: title,
                director: director,
                releaseYear: parseInt(releaseYear, 10),
            });
            navigate('/'); // Navigate back to the main page
        } catch (error) {
            console.error('Error adding movie:', error);
        }
    };

    const handleCancel = () => {
        navigate('/'); // Navigate back to the main page
    };

    return (
        <Container>
            <Box sx={{ mt: 4, display: 'flex', flexDirection: 'column', gap: 2 }}>
                <Typography variant="h4" component="h1" align="center" gutterBottom>
                    Add New Movie
                </Typography>
                <TextField
                    label="Title"
                    variant="outlined"
                    fullWidth
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    required
                />
                <TextField
                    label="Director"
                    variant="outlined"
                    fullWidth
                    value={director}
                    onChange={(e) => setDirector(e.target.value)}
                    required
                />
                <TextField
                    label="Release Year"
                    variant="outlined"
                    fullWidth
                    type="number"
                    value={releaseYear}
                    onChange={(e) => setReleaseYear(e.target.value)}
                    required
                />
                <Box sx={{ display: 'flex', gap: 2, mt: 3 }}>
                    <Button variant="contained" color="primary" onClick={handleSubmit}>
                        Save
                    </Button>
                    <Button variant="outlined" onClick={handleCancel}>
                        Cancel
                    </Button>
                </Box>
            </Box>
        </Container>
    );
};
