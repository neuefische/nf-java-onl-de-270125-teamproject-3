import MainLayout from "./components/MainLayout"
import React from 'react'
import './App.css'
import {Route, Routes} from "react-router";
import {SingleMovie} from "./components/SingleMovie.tsx";

const App: React.FC = () =>
{
    return (
        <Routes>
            <Route path="/" element={<MainLayout />} />
            <Route path="/:id" element={<SingleMovie />} />
        </Routes>
    );
};

export default App;

