INSERT INTO privileges (name, action, resource)
VALUES
  ('Create Privilege', 'privileges:create', 'privileges'),
  ('Read Privilege', 'privileges:read', 'privileges'),
  ('Update Privilege', 'privileges:update', 'privileges'),
  ('Delete Privilege', 'privileges:delete', 'privileges'),
  ('Create Role', 'roles:create', 'roles'),
  ('Read Role', 'roles:read', 'roles'),
  ('Update Role', 'roles:update', 'roles'),
  ('Delete Role', 'roles:delete', 'roles'),
  ('Create User', 'users:create', 'users'),
  ('Read User', 'users:read', 'users'),
  ('Update User', 'users:update', 'users'),
  ('Delete User', 'users:delete', 'users'),
  ('Read File', 'files:read', 'files'),
  ('Download File', 'files:download', 'files'),
  ('Upload File', 'files:upload', 'files'),
  ('Delete File', 'files:delete', 'files')
ON CONFLICT (action) DO NOTHING;

INSERT INTO roles (name) VALUES ('ADMIN') ON CONFLICT (name) DO NOTHING;

INSERT INTO roles_privileges (role_id, privilege_id)
SELECT r.id, p.id
FROM roles r
CROSS JOIN privileges p
WHERE r.name = 'ADMIN'
ON CONFLICT DO NOTHING;

INSERT INTO users (
  email,
  password,
  first_name,
  last_name,
  contact_number,
  role_id
)

VALUES (
  'admin@events.com',
  '$2y$12$E9K91gFKSle5AMfLlqDSwOU46iznKg764ah8BDcwGMI2RXuty.wUq',
  'Admin',
  'User',
  '+10000000000',
  (SELECT id FROM roles WHERE name = 'ADMIN')
)
ON CONFLICT (email) DO NOTHING;
