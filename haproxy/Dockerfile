FROM haproxy:latest

# Kopiowanie pliku konfiguracyjnego do obrazu
COPY haproxy.cfg /usr/local/etc/haproxy/haproxy.cfg

# Ustawienie domyślnego polecenia uruchamiającego HAProxy
CMD ["haproxy", "-f", "/usr/local/etc/haproxy/haproxy.cfg"]
