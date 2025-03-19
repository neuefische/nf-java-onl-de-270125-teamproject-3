import MainLayout from "./components/MainLayout"
import React from 'react'
import './App.css'
import {Route, Routes} from "react-router";
import {SingleMovie} from "./components/SingleMovie.tsx";
import EditMovieForm from "./components/EditMovieForm.tsx";
import AddMovieForm from "./components/AddMovieForm.tsx";

const App: React.FC = () =>
{
    return (
        <Routes>
            <Route path="/" element={<MainLayout />} />
            <Route path="/:id" element={<SingleMovie />} />
            <Route path="/:id/edit" element={<EditMovieForm />} />
            <Route path="/add" element={<AddMovieForm />} />

        </Routes>
    );
};

export default App;

