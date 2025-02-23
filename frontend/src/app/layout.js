import "./globals.css";

export const metadata = {
    title: "Moja aplikacja TODO",
    description: "Zarządzaj swoimi zadaniami!",
};

export default function RootLayout({ children }) {
    return (
        <html lang="pl">
        <body>{children}</body>
        </html>
    );
}
