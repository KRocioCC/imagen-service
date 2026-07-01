# Imagen Service

`imagen-service` es un microservicio encargado de administrar las imágenes de los productos del sistema e-commerce.

Su función principal es recibir imágenes, almacenarlas y permitir que puedan ser consultadas o eliminadas posteriormente.


Cuando se sube una imagen:

1. El archivo se guarda físicamente en MinIO.
2. La información de la imagen, como nombre, tipo, tamaño e identificador, se guarda en MongoDB.
3. El servicio devuelve un `imageId` y una URL.
4. `product-service` guarda esa referencia dentro del producto.

Las rutas se usan a través del API Gateway:

```text
http://localhost:9000
```

## Capturas

### Creando imagen en `imagen-service`

<img width="829" height="411" alt="Captura de Pantalla 2026-07-01 a la(s) 13 55 07" src="https://github.com/user-attachments/assets/6ec28b58-bc53-4ac1-bbf5-0a02f3324117" />

<br>

<img width="835" height="579" alt="Captura de Pantalla 2026-07-01 a la(s) 13 56 15" src="https://github.com/user-attachments/assets/2ca55ac9-ec33-4597-b0c2-5ed58a2c451b" />

<br>

<img width="1161" height="597" alt="Captura de Pantalla 2026-07-01 a la(s) 13 58 29" src="https://github.com/user-attachments/assets/e1d8044a-5610-44d9-b82a-6d36e58f9449" />

<br>

### Creando producto con la imagen en `product-service`

<img width="931" height="594" alt="Captura de Pantalla 2026-07-01 a la(s) 14 01 01" src="https://github.com/user-attachments/assets/66e0fb6d-e629-4b64-8f6d-6c96245aa7f8" />

<br>

<img width="1436" height="356" alt="Captura de Pantalla 2026-07-01 a la(s) 14 01 49" src="https://github.com/user-attachments/assets/8d98e2c2-19a0-474f-8009-715731e751de" />

<br>

### Imagen almacenada en MinIO

<img width="1438" height="769" alt="Captura de Pantalla 2026-07-01 a la(s) 14 03 40" src="https://github.com/user-attachments/assets/0da1f01f-58d8-4d4f-94e1-81bd6d13f1a2" />

<br>

### Eliminando producto e imágenes asociadas

Al eliminar un producto, también se eliminan automáticamente las imágenes vinculadas.

<img width="880" height="188" alt="Captura de Pantalla 2026-07-01 a la(s) 14 05 37" src="https://github.com/user-attachments/assets/e7d23636-fa18-420a-957b-f019519d083b" />

<br>

<img width="1440" height="537" alt="Captura de Pantalla 2026-07-01 a la(s) 14 06 02" src="https://github.com/user-attachments/assets/b10d6ee2-5cca-4493-8105-6818122fb400" />
