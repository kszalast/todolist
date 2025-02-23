export function Input({ className = "", ...props }) {
    return (
        <input
            className={`w-full p-2 border rounded-lg focus:ring-2 focus:ring-blue-500 focus:outline-none ${className}`}
            {...props}
        />
    );
}
