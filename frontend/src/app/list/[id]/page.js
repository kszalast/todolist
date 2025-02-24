"use client";
import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import { TrashIcon } from "@heroicons/react/24/solid";


export default function TodoListDetails() {
    const { id } = useParams();
    const router = useRouter();
    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        fetch(`/api/todolists/${id}/tasks`)
            .then((res) => res.json())
            .then((data) => setTasks(data));
    }, [id]);

    const [newTask, setNewTask] = useState("");

    const addTask = async () => {
        if (newTask.trim() === "") return;

        const response = await fetch(`/api/todolists/${id}/tasks`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name: newTask }),
        });

        const addedTask = await response.json();
        setTasks([...tasks, addedTask]);

        setNewTask("");
    };

    const removeTask = async (taskId) => {
        await fetch(`/api/todolists/${id}/tasks/${taskId}`, { method: "DELETE" });
        setTasks(tasks.filter((task) => task.id !== taskId));
    };

    return (
        <div className="min-h-screen bg-gray-900 text-white flex flex-col items-center p-6">
            <h1 className="text-3xl font-bold mb-6">Lista zadań #{id}</h1>

            {/* Input do dodawania zadania */}
            <div className="flex gap-2 mb-6">
                <input
                    type="text"
                    value={newTask}
                    onChange={(e) => setNewTask(e.target.value)}
                    placeholder="Nowe zadanie"
                    className="p-2 rounded-lg border border-gray-600 bg-gray-800 text-white"
                />
                <button
                    onClick={addTask}
                    className="px-4 py-2 bg-green-500 hover:bg-green-600 text-white font-semibold rounded-lg"
                >
                    Dodaj zadanie
                </button>
            </div>

            {/* Lista zadań */}
            <div className="w-full max-w-lg">
                {tasks.map((task) => (
                    <div
                        key={task.id}
                        className="flex justify-between items-center bg-gray-800 p-3 rounded-lg shadow-md mb-2"
                    >
                        <span>{task.description}</span>
                        <button
                            onClick={() => removeTask(task.id)}
                            className="bg-red-600 hover:bg-red-700 text-white w-8 h-8 flex items-center justify-center rounded-full shadow-md"
                        >
                            <TrashIcon className="w-5 h-5 text-white"/>
                        </button>
                    </div>
                ))}
            </div>

            {/* Przycisk powrotu */}
            <button
                onClick={() => router.push("/")}
                className="mt-6 px-4 py-2 bg-blue-500 hover:bg-blue-600 text-white font-semibold rounded-lg"
            >
                Powrót do list
            </button>
        </div>
    );
}
