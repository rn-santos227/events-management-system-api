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
  ('Delete File', 'files:delete', 'files'),
  ('Read Audit Logs', 'audit_logs:read', 'audit_logs'),
  ('Read Own Audit Logs', 'audit_logs:read:own', 'audit_logs')
ON CONFLICT (action) DO NOTHING;

INSERT INTO roles (name) VALUES ('ADMIN') ON CONFLICT (name) DO NOTHING;

INSERT INTO roles_privileges (role_id, privilege_id)
SELECT r.id, p.id
FROM roles r
CROSS JOIN privileges p
WHERE r.name = 'ADMIN'
ON CONFLICT DO NOTHING;
