"use client";
import { useEffect, useState } from "react";
import { useRouter } from "next/navigation";
import { TrashIcon } from "@heroicons/react/24/solid";


export default function Home() {
    const router = useRouter();
    const [lists, setLists] = useState([]);

    useEffect(() => {
        fetch("/api/todolists")
            .then((res) => res.json())
            .then((data) => setLists(data));
    }, []);

    const [newListName, setNewListName] = useState("");



    const addList = async () => {
        if (!newListName.trim()) return; // Zapobiega dodaniu pustej listy

        const response = await fetch("/api/todolists", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ name: newListName }),
        });

        const newList = await response.json();
        setLists([...lists, newList]);
        setNewListName(""); // Czyścimy pole po dodaniu
    };

    const removeList = async (id) => {
        await fetch(`/api/todolists/${id}`, { method: "DELETE" });
        setLists(lists.filter((list) => list.id !== id));
    };



    return (
        <div className="min-h-screen bg-gray-900 text-white flex flex-col items-center p-6">
            <h1 className="text-3xl font-bold mb-6">Moje listy TODO</h1>

            {/* Input do dodawania nowej listy */}
            <div className="flex gap-2 mb-6">
                <input
                    type="text"
                    value={newListName}
                    onChange={(e) => setNewListName(e.target.value)}
                    placeholder="Nazwa nowej listy"
                    className="p-2 rounded-lg border border-gray-600 bg-gray-800 text-white"
                />
                <button
                    onClick={addList}
                    className="px-4 py-2 bg-blue-500 hover:bg-blue-600 text-white font-semibold rounded-lg"
                >
                    Dodaj
                </button>
            </div>

            {/* Lista kafelków */}
            <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-4 w-full max-w-4xl">
                {lists.map((list) => (
                    <div
                        key={list.id}
                        onClick={() => router.push(`/list/${list.id}`)}
                        className="relative p-4 bg-gray-800 text-white rounded-xl shadow-lg hover:shadow-xl hover:bg-gray-700 transition cursor-pointer"
                    >
                        <h2 className="text-lg font-semibold">{list.name}</h2>
                        <button
                            onClick={(e) => {
                                e.stopPropagation();
                                removeList(list.id);
                            }}
                            className="absolute top-3 right-3 bg-red-600 hover:bg-red-700 text-white w-8 h-8 flex items-center justify-center rounded-full shadow-md"
                        >
                            <TrashIcon className="w-5 h-5 text-white"/>
                        </button>
                    </div>
                ))}
            </div>
        </div>
    );
}
