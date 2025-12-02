package com.example.bbdd.localdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database( // Indica que esta clase define una base de datos Room.
    entities = [UsuariosData::class, SesionData::class], version = 1, exportSchema = true
            // Especifica que la base de datos contiene una tabla representada por la entidad UsuariosData. Si tenemos
            // más tablas en la base de datos, se ponen a continuación separadas por comas.
        // Establece la versión actual de la base de datos, necesaria para el manejo de migraciones y cambios. Ir
            // incrementando a medida que se va modificando para notificar y adaptar los cambios.
        // Indica que la estructura de la base de datos se exportará a un archivo para mantener un historial de
// esquemas.
)
//AppDB hereda de RoomDatabase, que es la clase base para bases de datos Room.
abstract class AppDB: RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao // Metodo abstracto usuariosDao() para proporcionar instancias de
    // acceso a las operaciones definidas en la interfaz UsuarioDao. Room genera la implementación automáticamente.
    // Implementar este metodo para proporcionar la instancia funcional de acceso a los datos.
    abstract fun sesionDao(): SesionDao // Metodo abstracto sesionDao() para proporcionar instancias de acceso a las operaciones definidas en la interfaz SesionDao. Room genera la implementación automáticamente. Implementar
// este metodo para proporcionar la instancia funcional de acceso a los datos.
}
