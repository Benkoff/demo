CREATE TABLE `student` (
                      `uuid` binary(16) NOT NULL,
                      `label` varchar(255) NOT NULL,
                      `description` varchar(1023) DEFAULT NULL,
                      `version` int NOT NULL DEFAULT 0,
                      CONSTRAINT user_pk PRIMARY KEY (`uuid`),
                      INDEX user_label_idx (`label`)
);

INSERT INTO student(uuid, label, description)
VALUES (UUID_TO_BIN('7a7406fd-5e58-4c72-9afd-6dc4a4f4df1e'), 'Host', 'Host category'),
       (UUID_TO_BIN('0a421886-2149-4b9a-9890-5bed85f1ac7e'), 'Guest', 'Guest category'),
       (UUID_TO_BIN('aee1cb88-d5a2-4c71-a751-45a5c91385de'), 'Visitor', 'Visitor category');

CREATE TABLE `document` (
                          `uuid` binary(16) NOT NULL,
                          `label` varchar(255) NOT NULL,
                          `description` varchar(1023) DEFAULT NULL,
                          `user` binary(16) DEFAULT NULL,
#   `is_document_required` bit(1) NOT NULL DEFAULT b'0',
                          `version` int NOT NULL DEFAULT 0,
                          CONSTRAINT document_pk PRIMARY KEY (`uuid`),
                          INDEX document_label_idx (`label`)
);

INSERT INTO document(uuid, label, description, user)
VALUES (UUID_TO_BIN('7a7406fd-5e58-4c72-9afd-6dc4a4f4df1d'), 'Important', 'Please keep it', UUID_TO_BIN('7a7406fd-5e58-4c72-9afd-6dc4a4f4df1e')),
       (UUID_TO_BIN('0a421886-2149-4b9a-9890-5bed85f1ac7d'), 'Not Important', 'Could be removed', UUID_TO_BIN('7a7406fd-5e58-4c72-9afd-6dc4a4f4df1e'));
