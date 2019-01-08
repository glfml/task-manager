INSERT INTO users (id) VALUES (1);
INSERT INTO users (id) VALUES (2);
INSERT INTO users (id) VALUES (3);

INSERT INTO tasks (id, name, description, created_at, owner_id) VALUES (1, 'Task 1', 'Description 1', CURRENT_TIMESTAMP(), 1);
INSERT INTO tasks (id, name, description, created_at, owner_id) VALUES (2, 'Task 2', 'Description 2', CURRENT_TIMESTAMP(), 1);
INSERT INTO tasks (id, name, description, created_at, owner_id) VALUES (3, 'Task 3', 'Description 3', CURRENT_TIMESTAMP(), 1);
INSERT INTO tasks (id, name, description, created_at, owner_id) VALUES (4, 'Task 4', 'Description 4', CURRENT_TIMESTAMP(), 2);
INSERT INTO tasks (id, name, description, created_at, owner_id) VALUES (5, 'Task 5', 'Description 5', CURRENT_TIMESTAMP(), 3);

INSERT INTO tasks_users (task_id, user_id) VALUES (1, 2);
INSERT INTO tasks_users (task_id, user_id) VALUES (1, 3);
