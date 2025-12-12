INSERT INTO privileges (name, action, resource)
VALUES
  ('Create Category', 'categories:create', 'categories'),
  ('Read Category', 'categories:read', 'categories'),
  ('Update Category', 'categories:update', 'categories'),
  ('Delete Category', 'categories:delete', 'categories'),
  ('Create Personnel', 'personnel:create', 'personnel'),
  ('Read Personnel', 'personnel:read', 'personnel'),
  ('Update Personnel', 'personnel:update', 'personnel'),
  ('Delete Personnel', 'personnel:delete', 'personnel'),
  ('Create Vehicle', 'vehicles:create', 'vehicles'),
  ('Read Vehicle', 'vehicles:read', 'vehicles'),
  ('Update Vehicle', 'vehicles:update', 'vehicles'),
  ('Delete Vehicle', 'vehicles:delete', 'vehicles'),
  ('Create Venue', 'venues:create', 'venues'),
  ('Read Venue', 'venues:read', 'venues'),
  ('Update Venue', 'venues:update', 'venues'),
  ('Delete Venue', 'venues:delete', 'venues'),
  ('Create Accommodation', 'accommodations:create', 'accommodations'),
  ('Read Accommodation', 'accommodations:read', 'accommodations'),
  ('Update Accommodation', 'accommodations:update', 'accommodations'),
  ('Delete Accommodation', 'accommodations:delete', 'accommodations')
ON CONFLICT (action) DO NOTHING;
