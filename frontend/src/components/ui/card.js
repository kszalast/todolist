export function Card({ children, className, ...props }) {
    return (
        <div className={`rounded-xl border bg-white p-4 shadow-sm ${className}`} {...props}>
            {children}
        </div>
    );
}

export function CardContent({ children, className }) {
    return <div className={`p-2 ${className}`}>{children}</div>;
}
