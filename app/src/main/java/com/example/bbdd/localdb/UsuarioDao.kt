package com.example.bbdd.localdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsuarioDao {
    //Aquí van las Querys con las operaciones que se quieran realizar.
    //Insertar usuario
    @Insert
    fun nuevoUsuario(usuarioData: UsuariosData)
    //Leer toda la lista de usuarios
    @Query("SELECT * FROM ${Estructura.Usuario.TABLE_NAME}")
    fun getListaUsuarios(): List<UsuariosData>
    //Leer un usuario específico a través de su email
    @Query("SELECT * FROM ${Estructura.Usuario.TABLE_NAME} WHERE email = :email")
    fun getUnUser(email: String): UsuariosData?
    // En Room se debe usar parámetros con dos puntos (:email) para evitar inyección SQL y
    // permitir que Room gestione el parámetro que obtiene a través de la función. El uso de "?" hace que
    // siempre compruebe si el resultado es null antes de usarlo. Trata los nulos adecuadamente para
    // evitar excepciones de resultados no devueltos cuando se esperaba alguna fila. Es decir, añade
    // validaciones para asegurar que los campos están completos y bien formateados antes de
    // guardar.
}