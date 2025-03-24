import React, { useState } from "react";
import { IconButton, Dialog, DialogActions, DialogContent, DialogTitle, Button, Tooltip } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";
import axios from "axios";

interface DeleteButtonProps {
    movieId: string;
    movieTitle: string;
    onDeleteSuccess: () => void;
}

const DeleteButton: React.FC<DeleteButtonProps> = ({ movieId, movieTitle, onDeleteSuccess }) => {
    const [openDialog, setOpenDialog] = useState(false);

    const handleDelete = () => {
        axios
            .delete(`/api/movie/${movieId}`) // REST DELETE request
            .then(() => {
                setOpenDialog(false); // Close the dialog
                onDeleteSuccess(); // Notify parent to refresh movies
            })
            .catch((error) => {
                console.error("Error deleting movie:", error);
                alert("Failed to delete the movie. Please try again.");
            });
    };

    return (
        <>
            {/* Trash Icon Button */}
            <Tooltip title="Delete">
                <IconButton
                    onClick={() => setOpenDialog(true)}
                    color="error"
                    size="large"
                    aria-label="delete"
                    sx={{
                        // Optional: Customized styling
                        "&:hover": { backgroundColor: "rgba(255, 0, 0, 0.1)" },
                    }}
                >
                    <DeleteIcon />
                </IconButton>
            </Tooltip>

            {/* Confirmation Dialog */}
            <Dialog
                open={openDialog}
                onClose={() => setOpenDialog(false)}
                aria-labelledby="delete-dialog-title"
                aria-describedby="delete-dialog-description"
            >
                <DialogTitle id="delete-dialog-title">Delete Movie</DialogTitle>
                <DialogContent>
                    Are you sure you want to delete the movie <strong>{movieTitle}</strong>?
                    This action cannot be undone.
                </DialogContent>
                <DialogActions>
                    <Button onClick={() => setOpenDialog(false)} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={handleDelete} color="error" autoFocus>
                        Delete
                    </Button>
                </DialogActions>
            </Dialog>
        </>
    );
};

export default DeleteButton;