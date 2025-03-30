--
-- PostgreSQL database dump
--

-- Dumped from database version 17.4
-- Dumped by pg_dump version 17.4

-- Started on 2025-03-30 15:35:47

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 4849 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 16465)
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id bigint NOT NULL,
    name character varying(255),
    description character varying(255),
    price double precision,
    category character varying(255),
    unit_price double precision
);


ALTER TABLE public.products OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16472)
-- Name: productid; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.productid
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.productid OWNER TO postgres;

--
-- TOC entry 4850 (class 0 OID 0)
-- Dependencies: 218
-- Name: productid; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.productid OWNED BY public.products.id;


--
-- TOC entry 4842 (class 0 OID 16465)
-- Dependencies: 217
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.products VALUES (1000, 'Laptop', 'A high-performance laptop', 1200, 'Electronics', NULL);
INSERT INTO public.products VALUES (1001, 'Smartphone', 'Latest model smartphone', 800, 'Electronics', NULL);
INSERT INTO public.products VALUES (1002, 'Chair', 'Comfortable office chair', 150, 'Furniture', NULL);
INSERT INTO public.products VALUES (1004, 'Optical Mouse', 'This mouse is compatible with latest OS versions and is easy to configure in laptops and desktops.', NULL, 'Accessories', 1210);


--
-- TOC entry 4851 (class 0 OID 0)
-- Dependencies: 218
-- Name: productid; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.productid', 1005, true);


--
-- TOC entry 4696 (class 2606 OID 16471)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


-- Completed on 2025-03-30 15:35:48

--
-- PostgreSQL database dump complete
--

