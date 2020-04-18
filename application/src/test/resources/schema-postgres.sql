--
-- Name: category; Type: TYPE; Schema: public; Owner: test
--

CREATE TYPE public.category AS ENUM (
    'CLEANTECH',
    'E_COMMERCE',
    'EDTECH',
    'ENTERPRISETECH',
    'FINTECH',
    'FMCG',
    'HEALTHTECH',
    'MARKETPLACES',
    'MEDIA_AND_ENTERTAINMENT',
    'TRANSPORTTECH',
    'TRAVELTECH'
);

--
-- Name: city; Type: TYPE; Schema: public; Owner: test
--

CREATE TYPE public.city AS ENUM (
    'Mumbai',
    'Delhi',
    'Bangalore',
    'SF',
    'London'
);

--
-- Name: country; Type: TYPE; Schema: public; Owner: test
--

CREATE TYPE public.country AS ENUM (
    'India',
    'USA',
    'UK',
    'Australia'
);

--
-- Name: currency; Type: TYPE; Schema: public; Owner: test
--

CREATE TYPE public.currency AS ENUM (
    'INR',
    'USD',
    'EUR'
);

--
-- Name: funding_stage; Type: TYPE; Schema: public; Owner: test
--

CREATE TYPE public.funding_stage AS ENUM (
    'IPO',
    'MnA',
    'SEED_ROUND',
    'SERIES_A',
    'SERIES_B',
    'SERIES_C',
    'SERIES_D',
    'SERIES_DPLUS',
    'UNFUNDED',
    'UNKNOWN'
);

--
-- Name: job_status; Type: TYPE; Schema: public; Owner: test
--

CREATE TYPE public.job_status AS ENUM (
    'OPEN',
    'CLOSED'
);

--
-- Name: seniority_level; Type: TYPE; Schema: public; Owner: test
--

CREATE TYPE public.seniority_level AS ENUM (
    'INTERNSHIP',
    'ENTRY_LEVEL',
    'MID_SENIOR_LEVEL',
    'SENIOR_LEVEL',
    'DIRECTOR',
    'EXECUTIVE'
);

--
-- Name: companies_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.companies_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: companies; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.companies (
    id bigint DEFAULT nextval('public.companies_id_seq'::regclass) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    category public.category,
    funding_stage public.funding_stage NOT NULL,
    name character varying(255) NOT NULL,
    num_employees character varying(255),
    vision_statement text,
    website character varying(255) NOT NULL
);

--
-- Name: company_founder; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.company_founder (
    company_id bigint NOT NULL,
    founder_id bigint NOT NULL
);

--
-- Name: company_recruiter; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.company_recruiter (
    company_id bigint NOT NULL,
    recruiter_id bigint NOT NULL
);

--
-- Name: founders_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.founders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: founders; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.founders (
    id bigint DEFAULT nextval('public.founders_id_seq'::regclass) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    description text,
    name character varying(255) NOT NULL
);

--
-- Name: jobs_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.jobs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: jobs; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.jobs (
    id bigint DEFAULT nextval('public.jobs_id_seq'::regclass) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    city public.city NOT NULL,
    country public.country NOT NULL,
    description text,
    posting_date timestamp without time zone NOT NULL,
    seniority_level public.seniority_level NOT NULL,
    skills character varying[],
    status public.job_status NOT NULL,
    title character varying(255) NOT NULL,
    website_link character varying(255),
    company bigint NOT NULL,
    recruiter bigint NOT NULL,
    currency public.currency,
    max_salary integer,
    min_salary integer
);

--
-- Name: recruiters_id_seq; Type: SEQUENCE; Schema: public; Owner: test
--

CREATE SEQUENCE public.recruiters_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

--
-- Name: recruiters; Type: TABLE; Schema: public; Owner: test
--

CREATE TABLE public.recruiters (
    id bigint DEFAULT nextval('public.recruiters_id_seq'::regclass) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    email_id character varying(255) NOT NULL,
    name character varying(255) NOT NULL
);

--
-- Name: companies companies_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.companies
    ADD CONSTRAINT companies_pkey PRIMARY KEY (id);


--
-- Name: company_founder company_founder_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.company_founder
    ADD CONSTRAINT company_founder_pkey PRIMARY KEY (company_id, founder_id);


--
-- Name: company_recruiter company_recruiter_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.company_recruiter
    ADD CONSTRAINT company_recruiter_pkey PRIMARY KEY (company_id, recruiter_id);


--
-- Name: founders founders_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.founders
    ADD CONSTRAINT founders_pkey PRIMARY KEY (id);


--
-- Name: jobs jobs_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.jobs
    ADD CONSTRAINT jobs_pkey PRIMARY KEY (id);


--
-- Name: recruiters recruiters_pkey; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.recruiters
    ADD CONSTRAINT recruiters_pkey PRIMARY KEY (id);


--
-- Name: recruiters uk_4pi91limdvbqolynfu7everb1; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.recruiters
    ADD CONSTRAINT uk_4pi91limdvbqolynfu7everb1 UNIQUE (email_id);


--
-- Name: companies ukmxj4fcmk9gya75dkcv4bse2v; Type: CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.companies
    ADD CONSTRAINT ukmxj4fcmk9gya75dkcv4bse2v UNIQUE (name, website);


--
-- Name: jobs fk213mrj5brwleu3epkjcbkx5v4; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.jobs
    ADD CONSTRAINT fk213mrj5brwleu3epkjcbkx5v4 FOREIGN KEY (company) REFERENCES public.companies(id);


--
-- Name: jobs fk252tljfrjl964sgxu1uapj0vj; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.jobs
    ADD CONSTRAINT fk252tljfrjl964sgxu1uapj0vj FOREIGN KEY (recruiter) REFERENCES public.recruiters(id);


--
-- Name: company_recruiter fk32ads0in2avy2cj6snhyyeddd; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.company_recruiter
    ADD CONSTRAINT fk32ads0in2avy2cj6snhyyeddd FOREIGN KEY (recruiter_id) REFERENCES public.recruiters(id);


--
-- Name: company_founder fk98er25tevg0hsg1m4v35l693o; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.company_founder
    ADD CONSTRAINT fk98er25tevg0hsg1m4v35l693o FOREIGN KEY (founder_id) REFERENCES public.founders(id);


--
-- Name: company_recruiter fkds6n2cf0t3jl66nirocbqhbuj; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.company_recruiter
    ADD CONSTRAINT fkds6n2cf0t3jl66nirocbqhbuj FOREIGN KEY (company_id) REFERENCES public.companies(id);


--
-- Name: company_founder fksswv10901ivpmw9gvmvpg0xhu; Type: FK CONSTRAINT; Schema: public; Owner: test
--

ALTER TABLE ONLY public.company_founder
    ADD CONSTRAINT fksswv10901ivpmw9gvmvpg0xhu FOREIGN KEY (company_id) REFERENCES public.companies(id);


--
-- PostgreSQL database dump complete
--

