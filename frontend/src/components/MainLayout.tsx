
import React, {useEffect, useState} from "react";
import {
    AppBar,
    Toolbar,
    Typography,
    IconButton,
    Box,
    CssBaseline,
    Drawer,
    List,
    ListItem,
    ListItemText,
    TextField,
    Button,
    ThemeProvider,
    createTheme,
    Card,
    CardMedia,
    CardContent,
} from "@mui/material";
// @ts-ignore: Grid2 is typed incorrectly in the current version
import Grid2 from "@mui/material/Grid2";
import MenuIcon from "@mui/icons-material/Menu";
import AddIcon from "@mui/icons-material/Add";
import Brightness4Icon from "@mui/icons-material/Brightness4";
import Brightness7Icon from "@mui/icons-material/Brightness7";

import axios from "axios";
import {useNavigate} from "react-router";

interface Movie {
    id: string;
    title: string;
    director: string;
    releaseYear: number;
}

export type { Movie };

const MainLayout: React.FC = () => {
    const [movies, setMovies] = useState<Movie[]>([]);
    const [searchQuery, setSearchQuery] = useState<string>("");

    const moviesToDisplay = searchQuery ?
        movies.filter(movie => movie.title.toLowerCase().includes(searchQuery.toLowerCase()))
        : movies;

    const navigate = useNavigate()
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

    useEffect(() => {
        getMovies();
    }, [])

    const [sidebarOpen, setSidebarOpen] = useState(false);
    const [darkMode, setDarkMode] = useState(false);

    const handleSidebarToggle = () => setSidebarOpen(!sidebarOpen);
    const handleDarkModeToggle = () => setDarkMode(!darkMode);

    const theme = createTheme({
        palette: { mode: darkMode ? "dark" : "light" },
    });

    return (
        <ThemeProvider theme={theme}>
            <CssBaseline />
            {/* Header */}
            <Box sx={{ display: "flex", minHeight: "100vh" }}>
                {/* Header/Top Bar */}
                <AppBar
                    position="fixed"
                    color="primary"
                    sx={{
                        width: sidebarOpen ? "calc(100% - 260px)" : "100%", // Adjust width dynamically for sidebar
                        marginLeft: sidebarOpen ? "260px" : "0", // Push header when sidebar open
                        transition: (theme) =>
                            theme.transitions.create(["width", "margin-left"], {
                                duration: theme.transitions.duration.standard,
                            }),
                        zIndex: (theme) => theme.zIndex.drawer + 1,
                    }}
                >
                    <Toolbar>
                        <Typography variant="h6" sx={{ flexGrow: 1 }}>
                            My Movie Database
                        </Typography>
                        {/* Toggle Dark Mode */}
                        <IconButton
                            color="inherit"
                            onClick={handleDarkModeToggle}
                            title="Toggle Light/Dark Mode"
                        >
                            {darkMode ? <Brightness7Icon /> : <Brightness4Icon />}
                        </IconButton>
                    </Toolbar>
                </AppBar>

                {/* Sidebar */}
                <Drawer
                    variant="persistent"
                    anchor="left"
                    open={sidebarOpen}
                    sx={{
                        "& .MuiDrawer-paper": { width: 260, boxSizing: "border-box" },
                    }}
                >
                    <Box sx={{ padding: "1rem" }}>
                        {/* Sidebar Content */}
                        <Typography variant="h6" gutterBottom>
                            Movie Tools
                        </Typography>
                        <Button
                            variant="contained"
                            color="primary"
                            startIcon={<AddIcon />}
                            fullWidth
                            sx={{ marginBottom: "1rem" }}
                        >
                            Add Movie
                        </Button>
                        <TextField
                            fullWidth
                            variant="outlined"
                            value={searchQuery}
                            onChange = {(event) => setSearchQuery(event.target.value)}
                            placeholder="Search movies..."
                            sx={{ marginBottom: "1rem" }}
                        />
                        <Typography variant="body1" gutterBottom>
                            Filter by Tag
                        </Typography>
                        <List>
                            <ListItem component="button">
                                <ListItemText primary="Action" />
                            </ListItem>
                            <ListItem component="button">
                                <ListItemText primary="Comedy" />
                            </ListItem>
                            <ListItem component="button">
                                <ListItemText primary="Drama" />
                            </ListItem>
                        </List>
                    </Box>
                </Drawer>

                {/* Sidebar Toggle Button */}
                <IconButton
                    onClick={handleSidebarToggle}
                    sx={{
                        position: "fixed",
                        top: "16px",
                        left: sidebarOpen ? "270px" : "16px",
                        zIndex: (theme) => theme.zIndex.drawer + 2,
                        transition: (theme) =>
                            theme.transitions.create("left", {
                                duration: theme.transitions.duration.standard,
                            }),
                    }}
                >
                    <MenuIcon />
                </IconButton>

                {/* Main Content Area */}
                <Box
                    component="main"
                    sx={{
                        flexGrow: 1,
                        marginTop: "64px", // Align with AppBar height
                        marginLeft: sidebarOpen ? "260px" : "0", // Adjust for sidebar
                        transition: (theme) =>
                            theme.transitions.create(["margin-left", "width"], {
                                duration: theme.transitions.duration.standard,
                            }),
                        padding: "1rem", // Inner padding for movies
                    }}
                >
                    {/* Movie Grid */}
                    <Grid2 container spacing={3}>
                        {moviesToDisplay.map((movie) => (
                            // @ts-expect-error: Component requires props not yet typed
                            <Grid2
                                xs={12}
                                sm={6}
                                md={4}
                                lg={3}
                                key={movie.id}
                                sx={{ display: "flex", justifyContent: "center" }}
                            >
                            <Card sx={{ maxWidth: 345, width: "100%" }}>
                                    <CardMedia
                                        component="img"
                                        height="140"
                                        image="https://via.placeholder.com/300x140?text=Movie+Image"
                                        alt="Movie Poster"
                                    />
                                    <CardContent>
                                        <Typography gutterBottom variant="h6">
                                            {movie.title}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            Director: {movie.director}
                                        </Typography>
                                        <Typography variant="body2" color="text.secondary">
                                            Release Year: {movie.releaseYear}
                                        </Typography>
                                        <Button
                                            variant="contained"
                                            color="primary"
                                            fullWidth
                                            onClick={() => navigate(`/${movie.id}`)}
                                        >
                                            View Details
                                        </Button>
                                    </CardContent>
                                </Card>
                            </Grid2>
                        ))}
                    </Grid2>
                </Box>
            </Box>
        </ThemeProvider>
    );
};

export default MainLayout;
