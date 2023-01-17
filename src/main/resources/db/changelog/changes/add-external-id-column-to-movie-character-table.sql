--liquibase formatted sql
--changeset <oryshak>:<add-external-id-column-to-movie-character-table>
ALTER TABLE public.movie_characters ADD external_id bigint NOT NULL;

--rollback ALTER TABLE public.movie_characters DROP COLUMN external_id;
