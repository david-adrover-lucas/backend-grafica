package com.drover.demo.backend.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * Ajustes chicos para bases existentes. Hibernate update agrega columnas nuevas,
 * pero no siempre relaja NOT NULL en columnas ya creadas.
 */
@Component
public class DatabaseSchemaCompatibility implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseSchemaCompatibility(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) {
        ejecutarSiSePuede("ALTER TABLE ventas MODIFY COLUMN usuario_id INT NULL");
        ejecutarSiSePuede("ALTER TABLE presupuestos MODIFY COLUMN usuario_id INT NULL");
    }

    private void ejecutarSiSePuede(String sql) {
        try {
            jdbcTemplate.execute(sql);
        } catch (Exception ignored) {
            // Si el motor no lo necesita o el usuario de BD no puede alterarlo,
            // la app puede seguir funcionando con el esquema ya existente.
        }
    }
}
