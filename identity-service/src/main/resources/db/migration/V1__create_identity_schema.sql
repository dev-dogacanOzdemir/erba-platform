CREATE SCHEMA IF NOT EXISTS identity;

CREATE TABLE identity.identity_users (
                                         id UUID PRIMARY KEY,
                                         auth_user_id UUID,

                                         user_type VARCHAR(50) NOT NULL,
                                         status VARCHAR(50) NOT NULL,

                                         email VARCHAR(150),
                                         first_name VARCHAR(100) NOT NULL,
                                         last_name VARCHAR(100) NOT NULL,
                                         phone VARCHAR(30),

                                         profile_photo_id UUID,

                                         created_at TIMESTAMP NOT NULL,
                                         updated_at TIMESTAMP,
                                         deleted_at TIMESTAMP
);

CREATE UNIQUE INDEX ux_identity_users_email
    ON identity.identity_users(email)
    WHERE email IS NOT NULL AND deleted_at IS NULL;

CREATE INDEX ix_identity_users_auth_user_id
    ON identity.identity_users(auth_user_id);

CREATE TABLE identity.employee_profiles (
                                            id UUID PRIMARY KEY,
                                            identity_user_id UUID NOT NULL,

                                            employee_number VARCHAR(50),
                                            department VARCHAR(80) NOT NULL,
                                            position VARCHAR(80) NOT NULL,
                                            employment_type VARCHAR(50) NOT NULL,

                                            hire_date DATE,
                                            termination_date DATE,
                                            birth_date DATE,

                                            created_at TIMESTAMP NOT NULL,
                                            updated_at TIMESTAMP,

                                            CONSTRAINT fk_employee_profiles_identity_user
                                                FOREIGN KEY (identity_user_id)
                                                    REFERENCES identity.identity_users(id)
);

CREATE UNIQUE INDEX ux_employee_profiles_identity_user_id
    ON identity.employee_profiles(identity_user_id);

CREATE UNIQUE INDEX ux_employee_profiles_employee_number
    ON identity.employee_profiles(employee_number)
    WHERE employee_number IS NOT NULL;

CREATE TABLE identity.employee_sensitive_info (
                                                  id UUID PRIMARY KEY,
                                                  identity_user_id UUID NOT NULL,

                                                  national_id VARCHAR(20),
                                                  sgk_number VARCHAR(50),

                                                  created_at TIMESTAMP NOT NULL,
                                                  updated_at TIMESTAMP,

                                                  CONSTRAINT fk_employee_sensitive_info_identity_user
                                                      FOREIGN KEY (identity_user_id)
                                                          REFERENCES identity.identity_users(id)
);

CREATE UNIQUE INDEX ux_employee_sensitive_info_identity_user_id
    ON identity.employee_sensitive_info(identity_user_id);

CREATE UNIQUE INDEX ux_employee_sensitive_info_national_id
    ON identity.employee_sensitive_info(national_id)
    WHERE national_id IS NOT NULL;