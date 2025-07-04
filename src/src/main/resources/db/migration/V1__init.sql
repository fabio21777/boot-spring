
CREATE TABLE public."_user" (
	id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	email varchar(255) NOT NULL,
	firstname varchar(255) NOT NULL,
	lastname varchar(255) NULL,
	"password" varchar(255) NOT NULL,
	"role" varchar(255) NULL,
	created_at timestamp(6) NOT NULL,
	"uuid" varchar(64) NOT NULL,
	CONSTRAINT "_user_pkey" PRIMARY KEY (id),
	CONSTRAINT "_user_role_check" CHECK (((role)::text = ANY ((ARRAY['USER'::character varying, 'ADMIN'::character varying, 'MANAGER'::character varying])::text[]))),
	CONSTRAINT ukb5qkwp2bl5lmiammlsrg94h7o UNIQUE (uuid),
	CONSTRAINT ukk11y3pdtsrjgy8w9b6q4bjwrx UNIQUE (email)
);


-- public.file definição

-- Drop table

-- DROP TABLE public.file;

CREATE TABLE public.file (
	id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	created_at timestamp(6) NOT NULL,
	"uuid" varchar(64) NOT NULL,
	bucket varchar(64) NOT NULL,
	deleted bool NULL,
	original_name varchar(255) NOT NULL,
	user_id int8 NOT NULL,
	CONSTRAINT file_pkey PRIMARY KEY (id),
	CONSTRAINT uklceljor7aildbvbvl6pa7wimj UNIQUE (uuid),
	CONSTRAINT fkedgh8m43sbs8d74uh1rc4fosq FOREIGN KEY (user_id) REFERENCES public."_user"(id)
);


-- public.file_download definição

-- Drop table

-- DROP TABLE public.file_download;

CREATE TABLE public.file_download (
	id int8 GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE) NOT NULL,
	created_at timestamp(6) NOT NULL,
	"uuid" varchar(64) NOT NULL,
	file_id int8 NOT NULL,
	user_id int8 NOT NULL,
	CONSTRAINT file_download_pkey PRIMARY KEY (id),
	CONSTRAINT ukm42htaqdspvqpua8giudybh3k UNIQUE (uuid),
	CONSTRAINT fkle31gyh3w3bvwg6c7xcimkkv9 FOREIGN KEY (file_id) REFERENCES public.file(id),
	CONSTRAINT fkpoir92rc5vhqo0l16x9cchpuy FOREIGN KEY (user_id) REFERENCES public."_user"(id)
);


-- public."token" definição

-- Drop table

-- DROP TABLE public."token";

CREATE TABLE public."token" (
	id int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY( INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 CACHE 1 NO CYCLE),
	expired bool NOT NULL,
	revoked bool NOT NULL,
	"token" varchar(255) NULL,
	token_type varchar(255) NULL,
	user_id int8 NULL,
	created_at timestamp(6) NOT NULL,
	"uuid" varchar(64) NOT NULL,
	CONSTRAINT token_pkey PRIMARY KEY (id),
	CONSTRAINT token_token_type_check CHECK (((token_type)::text = 'BEARER'::text)),
	CONSTRAINT ukk74apnrvhamiburvb8hjwuh7h UNIQUE (uuid),
	CONSTRAINT ukpddrhgwxnms2aceeku9s2ewy5 UNIQUE (token),
	CONSTRAINT fkiblu4cjwvyntq3ugo31klp1c6 FOREIGN KEY (user_id) REFERENCES public."_user"(id)
);