_CREATE TABLE categories (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  full_name VARCHAR(255) NOT NULL UNIQUE,
  description TEXT,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP
);

CREATE TABLE personnel (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  full_name VARCHAR(255) NOT NULL,
  contact_number VARCHAR(20) NOT NULL,
  function VARCHAR(255),
  email VARCHAR(100) NOT NULL,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP
);

CREATE TABLE vehicles (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(255) NOT NULL,
  type VARCHAR(50) NOT NULL,
  status VARCHAR(50) NOT NULL,
  contact_number VARCHAR(20) NOT NULL,
  plate_number VARCHAR(50) NOT NULL,
  personnel_id UUID,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP,
  CONSTRAINT fk_vehicles_personnel FOREIGN KEY (personnel_id) REFERENCES personnel (id)
);

CREATE TABLE venues (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(255) NOT NULL,
  address TEXT NOT NULL,
  contact_person VARCHAR(255) NOT NULL,
  contact_number VARCHAR(20),
  email VARCHAR(100) NOT NULL,
  type VARCHAR(50) NOT NULL,
  latitude NUMERIC(9,6),
  longitude NUMERIC(9,6),
  image_id UUID,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP,
  CONSTRAINT fk_venues_image FOREIGN KEY (image_id) REFERENCES stored_files (id)
);

CREATE TABLE accommodations (
  id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
  name VARCHAR(255) NOT NULL,
  address TEXT NOT NULL,
  contact_person VARCHAR(255) NOT NULL,
  contact_number VARCHAR(20),
  email VARCHAR(100) NOT NULL,
  type VARCHAR(50) NOT NULL,
  latitude NUMERIC(9,6),
  longitude NUMERIC(9,6),
  image_id UUID,
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  deleted_at TIMESTAMP,
  CONSTRAINT fk_accommodations_image FOREIGN KEY (image_id) REFERENCES stored_files (id)
);
