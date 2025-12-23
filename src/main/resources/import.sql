-- Activities
insert into Activity (id, name, description, create_date) values (gen_random_uuid(), 'Learning Track', 'Learning Track',NOW());
insert into Activity (id, name, description, create_date) values (gen_random_uuid(), 'Bootcamp', 'Bootcamp',NOW());
insert into Activity (id, name, description, create_date) values (gen_random_uuid(), 'Hackathon', 'Hackathon',NOW());


-- Tracks and Profiles

insert into domain (id, name, description, community, icon, create_date) values (gen_random_uuid(), 'Software & Application Engineering', 'Building and maintaining applications and services', 'Contribute to startup MVPs, open-source projects, or product incubators','Code2',NOW());
insert into domain (id, name, description, community, icon, create_date) values (gen_random_uuid(), 'Cloud, DevOps & Infrastructure', 'Systems automation, scalability, and reliability', 'Design cloud labs, support startup deployments, optimize environments','Cloud',NOW());
insert into domain (id, name, description, community, icon, create_date) values (gen_random_uuid(), 'Data, AI & Analytics', 'Turning data into intelligence and automation', 'Collaborate on AI-based projects, participate in data challenges, train and deploy ML models','Database',NOW());
insert into domain (id, name, description, community, icon, create_date) values (gen_random_uuid(), 'Cybersecurity & Compliance', 'Ensuring systems, data, and users remain secure', 'Conduct vulnerability audits, lead security workshops, mentor startups on best practices','Shield',NOW());
insert into domain (id, name, description, community, icon, create_date) values (gen_random_uuid(), 'Architecture & IT Strategy', 'Shaping large-scale design and technology direction', 'Mentor project teams, review architectures, define incubator blueprints','Layers',NOW());
insert into domain (id, name, description, community, icon, create_date) values (gen_random_uuid(), 'Product, Design & UX', 'Product usability, user experience, and customer value', 'Collaborate with developers and startups to design user-centric products','Palette',NOW());
insert into domain (id, name, description, community, icon, create_date) values (gen_random_uuid(), 'IT Management & Leadership', 'Driving execution, alignment, and delivery across teams', 'Facilitate incubator programs, coordinate sprints, manage resources, and coach teams','Users',NOW());
insert into domain (id, name, description, community, icon, create_date) values (gen_random_uuid(), 'Business Technology & Digital Transformation', 'At the intersection of business and technology', 'Work with startups to translate business needs into tech solutions, validate market fits','Briefcase',NOW());
insert into domain (id, name, description, community, icon, create_date) values (gen_random_uuid(), 'Educators, Mentors & Community Contributors', 'Sharing expertise and guiding others', 'Support incubator programs, evaluate contributions, lead workshops or courses','GraduationCap',NOW());
insert into domain (id, name, description, community, icon, create_date) values (gen_random_uuid(), 'Emerging Tech & Research Specialists', 'Working on new frontiers', 'Lead exploratory projects, create PoCs, and contribute to innovation tracks','Sparkles',NOW());



insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Frontend Developer', 'Frontend Developer (React, Angular, Nextjs, Vue)', 'JavaScript, TypeScript, React, Angular, Next.js, Vue.js, HTML, CSS, Bootstrap',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Backend Developer', 'Backend Developer (Java, Python, Node.js, Go)', 'Java, Python, Node.js, Go, RESTful APIs, databases (SQL/NoSQL)',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Full Stack Engineer', 'Full Stack Engineer', 'JavaScript, TypeScript, React, Node.js, databases (SQL/NoSQL), RESTful APIs',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Mobile Developer', 'Mobile Developer (iOS / Android / Flutter)', 'Swift, Kotlin, Flutter, React Native, mobile app development',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Game Developer / XR Engineer', 'Game Developer / XR Engineer', 'Unity, Unreal Engine, C#, C++, AR/VR development',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Software Engineer in Test', 'Software Engineer in Test (QA Automation)', 'QA Automation, Selenium, JUnit, Cucumber',NOW());

INSERT INTO domain_profiles (domain_id, profile_id)
SELECT d.id, p.id
FROM domain d
JOIN profile p
  ON p.name in (select name from profile)
WHERE d.name = 'Software & Application Engineering';


insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'DevOps Engineer', 'DevOps Engineer', 'DevOps, GitOps, delivery pipelines, CI/CD, Kubernetes, Docker, Jenkins, Ansible, Terraform',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Site Reliability Engineer (SRE)', 'Site Reliability Engineer (SRE)', 'automation, monitoring, incident response, SLIs/SLOs, Kubernetes, Prometheus, Grafana',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Cloud Architect (AWS, Azure, GCP, OpenShift)', 'Cloud Architect (AWS, Azure, GCP, OpenShift)', 'cloud architecture, AWS, Azure, GCP, Kubernetes, OpenShift, microservices, ServiceMesh, Skupper, serverless',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Systems Engineer / Administrator', 'Systems Engineer / Administrator', 'Linux, Windows Server, networking, virtualization, cloud platforms, scripting (Bash, Python)',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Infrastructure as Code Specialist (Terraform, Ansible)', 'Infrastructure as Code Specialist (Terraform, Ansible)', 'Terraform, Ansible, cloud provisioning, configuration management, automation scripting',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Network Engineer', 'Network Engineer', 'network design, routing, switching, firewalls, VPNs, network security, cloud networking',NOW());

INSERT INTO domain_profiles (domain_id, profile_id)
SELECT d.id, p.id
FROM domain d
JOIN profile p
  ON p.name in (select name from profile where id not in (select profile_id from domain_profiles))
WHERE d.name = 'Cloud, DevOps & Infrastructure';


insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Data Engineer', 'Data Engineer', 'ETL, data pipelines, SQL, NoSQL, big data technologies (Hadoop, Spark), data warehousing',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Data Scientist', 'Data Scientist', 'statistics, machine learning, Python/R, data visualization, big data tools (Hadoop, Spark)',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Machine Learning Engineer', 'Machine Learning Engineer', 'machine learning, deep learning, Python, TensorFlow, PyTorch, model deployment',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'AI Researcher / Model Developer', 'AI Researcher / Model Developer', 'AI algorithms, research methodologies, Python, TensorFlow, PyTorch, model development',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Business Intelligence Analyst', 'Business Intelligence Analyst', 'data analysis, SQL, data visualization tools (Tableau, Power BI), reporting',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'MLOps Engineer', 'MLOps Engineer', 'MLOps, model deployment, CI/CD for ML, Kubernetes, Docker, monitoring',NOW());

INSERT INTO domain_profiles (domain_id, profile_id)
SELECT d.id, p.id
FROM domain d
JOIN profile p
  ON p.name in (select name from profile where id not in (select profile_id from domain_profiles))
WHERE d.name = 'Data, AI & Analytics';


insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(),'Security Engineer', 'Security Engineer', 'vulnerability assessment, penetration testing, security tools, incident response',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Ethical Hacker / Penetration Tester', 'Ethical Hacker / Penetration Tester', 'penetration testing, vulnerability assessment, security tools, reporting',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'SOC Analyst', 'SOC Analyst', 'security monitoring, incident response, SIEM tools, threat analysis',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Security Architect', 'Security Architect', 'security architecture, risk management, compliance, security frameworks',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'GRC (Governance, Risk, Compliance) Specialist', 'GRC (Governance, Risk, Compliance) Specialist', 'governance, risk management, compliance frameworks, audits',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'DevSecOps Engineer', 'DevSecOps Engineer', 'DevSecOps, security automation, CI/CD security, Kubernetes security, container security',NOW());

INSERT INTO domain_profiles (domain_id, profile_id)
SELECT d.id, p.id
FROM domain d
JOIN profile p
  ON p.name in (select name from profile where id not in (select profile_id from domain_profiles))
WHERE d.name = 'Cybersecurity & Compliance';

insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Solution Architect', 'Solution Architect', 'system design, architecture patterns, cloud platforms, microservices',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Enterprise Architect', 'Enterprise Architect', 'enterprise architecture, TOGAF, system integration, technology strategy',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Technical Product Owner', 'Technical Product Owner', 'product management, agile methodologies, stakeholder communication',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Platform Architect', 'Platform Architect', 'platform design, cloud services, scalability, microservices architecture',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Integration Specialist', 'Integration Specialist', 'system integration, APIs, middleware, data exchange protocols',NOW());

INSERT INTO domain_profiles (domain_id, profile_id)
SELECT d.id, p.id
FROM domain d
JOIN profile p
  ON p.name in (select name from profile where id not in (select profile_id from domain_profiles))
WHERE d.name = 'Architecture & IT Strategy';


insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(),  'Product Manager', 'Product Manager', 'product strategy, roadmap planning, stakeholder management',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(),  'UX/UI Designer', 'UX/UI Designer', 'user experience design, user interface design, prototyping tools (Figma, Sketch)',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(),  'Interaction Designer', 'Interaction Designer', 'interaction design, user research, prototyping, usability testing',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'User Researcher', 'User Researcher', 'user research methodologies, data analysis, usability testing',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(),  'Design Systems Specialist', 'Design Systems Specialist', 'design systems, component libraries, UI frameworks',NOW());

INSERT INTO domain_profiles (domain_id, profile_id)
SELECT d.id, p.id
FROM domain d
JOIN profile p
  ON p.name in (select name from profile where id not in (select profile_id from domain_profiles))
WHERE d.name = 'Product, Design & UX';


insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(),'Project Manager / Scrum Master', 'Project Manager / Scrum Master', 'project management, agile methodologies, team facilitation',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Delivery Manager', 'Delivery Manager', 'delivery management, stakeholder communication, risk management',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'IT Operations Manager', 'IT Operations Manager', 'IT operations, team leadership, process improvement',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Technical Program Manager', 'Technical Program Manager', 'program management, technical leadership, cross-functional coordination',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Engineering Manager', 'Engineering Manager', 'team leadership, software development processes, talent development',NOW());

INSERT INTO domain_profiles (domain_id, profile_id)
SELECT d.id, p.id
FROM domain d
JOIN profile p
  ON p.name in (select name from profile where id not in (select profile_id from domain_profiles))
WHERE d.name = 'IT Management & Leadership';


insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Business Analyst', 'Business Analyst', 'requirements gathering, stakeholder communication, process modeling',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'IT Consultant', 'IT Consultant', 'IT strategy, technology assessment, stakeholder engagement',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Digital Transformation Lead', 'Digital Transformation Lead', 'digital strategy, change management, technology adoption',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'ERP / CRM Specialist', 'ERP / CRM Specialist', 'ERP systems, CRM platforms, business process optimization',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Cloud Adoption Consultant', 'Cloud Adoption Consultant', 'cloud strategy, migration planning, stakeholder engagement',NOW());

INSERT INTO domain_profiles (domain_id, profile_id)
SELECT d.id, p.id
FROM domain d
JOIN profile p
  ON p.name in (select name from profile where id not in (select profile_id from domain_profiles))
WHERE d.name = 'Business Technology & Digital Transformation';

insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Mentor / Coach', 'Mentor / Coach', 'mentoring, coaching, technical expertise',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Technical Trainer', 'Technical Trainer', 'technical training, curriculum development, instructional design',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Community Manager', 'Community Manager', 'community engagement, event planning, communication skills',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Documentation Specialist', 'Documentation Specialist', 'technical writing, documentation tools, content management',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Open Source Maintainer', 'Open Source Maintainer', 'open source contribution, project management, community engagement',NOW());

INSERT INTO domain_profiles (domain_id, profile_id)
SELECT d.id, p.id
FROM domain d
JOIN profile p
  ON p.name in (select name from profile where id not in (select profile_id from domain_profiles))
WHERE d.name = 'Educators, Mentors & Community Contributors';


insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Blockchain Developer', 'Blockchain Developer', 'blockchain platforms (Ethereum, Hyperledger), smart contracts, decentralized applications',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'IoT Engineer', 'IoT Engineer', 'IoT platforms, embedded systems, sensor networks, data protocols',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'AR/VR Specialist', 'AR/VR Specialist', 'augmented reality, virtual reality, Unity, Unreal Engine',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Quantum Computing Researcher', 'Quantum Computing Researcher', 'quantum algorithms, quantum programming languages (Qiskit, Cirq), quantum hardware',NOW());
insert into profile (id, name, description, skills, create_date) values (gen_random_uuid(), 'Edge Computing Architect', 'Edge Computing Architect', 'edge computing, distributed systems, IoT integration, cloud-edge architectures',NOW());

INSERT INTO domain_profiles (domain_id, profile_id)
SELECT d.id, p.id
FROM domain d
JOIN profile p
  ON p.name in (select name from profile where id not in (select profile_id from domain_profiles))
WHERE d.name = 'Emerging Tech & Research Specialists';


-- Journey, Stages, and Milestones

--////////////////////////////////
-- /// IT Professional Jounrwy ///
--////////////////////////////////
WITH new_journey AS (
    INSERT INTO journey (id, name, title, description)
    VALUES (
        gen_random_uuid(),
        'IT Professional',
        'Your path from foundation to global impact through 5 progressive stages of learning, contribution, and leadership.',
        'From foundation to global impact through 5 progressive stages of learning, contribution, and leadership.'
    )
    RETURNING id
)
INSERT INTO stage (id, sequence, name, description, icon, journey_id)
SELECT
    gen_random_uuid(),
    s.sequence,
    s.name,
    s.description,
    s.icon,
    j.id
FROM new_journey j
JOIN (
    VALUES
        (1, 'Foundation', 'Onboarding & Learning', 'BookOpen'),
        (2, 'Practice', 'Real Use Cases', 'Blocks'),
        (3, 'Contribution', 'Projects & Open Source', 'GitPullRequest'),
        (4, 'Growth', 'Mentorship & Leadership', 'Users'),
        (5, 'Impact', 'Career & Global Influence', 'TrendingUp')
) AS s(sequence, name, description, icon)
ON TRUE;


-- add milestones
-- stage 1
WITH stage_seq_1 AS (
    SELECT id
    FROM stage
    WHERE sequence = 1
)
INSERT INTO milestone (
    id,
    sequence,
    required_points,
    name,
    description,
    stage_id,
    skippable
)
SELECT
    gen_random_uuid(),
    m.sequence,
    m.required_points,
    m.name,
    m.description,
    s.id,
    m.skippable
FROM stage_seq_1 s
JOIN (
    VALUES
        (1,   1,   'Join the community, set up profile',        'Join the community, set up profile',        false),
        (2,  10,   'Enroll in bootcamps & hands-on labs',       'Enroll in bootcamps & hands-on labs',       false),
        (3, 300,   'Work toward certifications',               'Work toward certifications',               true)
) AS m(sequence, required_points, name, description, skippable)
ON TRUE;

-- stage 2
WITH stage_seq_1 AS (
    SELECT id
    FROM stage
    WHERE sequence = 2
)
INSERT INTO milestone (
    id,
    sequence,
    required_points,
    name,
    description,
    stage_id,
    skippable
)
SELECT
    gen_random_uuid(),
    m.sequence,
    m.required_points,
    m.name,
    m.description,
    s.id,
    m.skippable
FROM stage_seq_1 s
JOIN (
    VALUES
        (4, 10,    'Join hackathons & coding challenges', 'Join hackathons & coding challenges',false),
        (5, 10,    'Work on customer use-case simulations', 'Work on customer use-case simulations',false),
        (6, 10,    'Start building GitHub portfolio', 'Start building GitHub portfolio',false)
) AS m(sequence, required_points, name, description, skippable)
ON TRUE;

-- stage 3
WITH stage_seq_1 AS (
    SELECT id
    FROM stage
    WHERE sequence = 3
)
INSERT INTO milestone (
    id,
    sequence,
    required_points,
    name,
    description,
    stage_id,
    skippable
)
SELECT
    gen_random_uuid(),
    m.sequence,
    m.required_points,
    m.name,
    m.description,
    s.id,
    m.skippable
FROM stage_seq_1 s
JOIN (
    VALUES
        (7, 10,    'Contribute to open source projects', 'Contribute to open source projects',false),
        (8, 10,    'Join team incubators (cross-functional squads)', 'Join team incubators (cross-functional squads)',false),
        (9, 10,    'Build reputation via successful contributions', 'Build reputation via successful contributions',false)
) AS m(sequence, required_points, name, description, skippable)
ON TRUE;
        
--stage 4
WITH stage_seq_1 AS (
    SELECT id
    FROM stage
    WHERE sequence = 4
)
INSERT INTO milestone (
    id,
    sequence,
    required_points,
    name,
    description,
    stage_id,
    skippable
)
SELECT
    gen_random_uuid(),
    m.sequence,
    m.required_points,
    m.name,
    m.description,
    s.id,
    m.skippable
FROM stage_seq_1 s
JOIN (
    VALUES
        (10, 10,    'Become a mentor for new members', 'Become a mentor for new members',false),
        (11, 10,    'Publish blogs, give talks, advocate technologies', 'Publish blogs, give talks, advocate technologies',false),
        (12, 10,    'Guide teams & incubators', 'Guide teams & incubators',false)
) AS m(sequence, required_points, name, description, skippable)
ON TRUE;
        
-- stage 5
WITH stage_seq_1 AS (
    SELECT id
    FROM stage
    WHERE sequence = 5
)
INSERT INTO milestone (
    id,
    sequence,
    required_points,
    name,
    description,
    stage_id,
    skippable
)
SELECT
    gen_random_uuid(),
    m.sequence,
    m.required_points,
    m.name,
    m.description,
    s.id,
    m.skippable
FROM stage_seq_1 s
JOIN (
    VALUES
        (13, 10,    'Land jobs or build startups', 'Land jobs or build startups',false),
        (14, 10,    'Launch impactful community projects', 'Launch impactful community projects',false),
        (15, 10,    'Scale contributions across industries', 'Scale contributions across industries',false)
) AS m(sequence, required_points, name, description, skippable)
ON TRUE;

-- add milestone acitivity (milestone sequence 2)
WITH target_milestone AS (
    SELECT m.id AS milestone_id
    FROM milestone m
    WHERE m.sequence = 2
)
INSERT INTO milestone_activity (milestone_id, activity_id)
SELECT tm.milestone_id, a.id
FROM target_milestone tm
CROSS JOIN activity a;


--////////////////////////////////
--///  recruiter journey       ///
--////////////////////////////////
-- add journey and stages
WITH new_journey AS (
    INSERT INTO journey (id, name, title, description)
    VALUES (
        gen_random_uuid(),
        'Recruiter',
        'Discover, invest in, and hire top talent while building your brand in the community through 5 strategic stages.', 
        'Discover, invest in, and hire top talent while building your brand in the community.'
    )
    RETURNING id
)
INSERT INTO stage (id, sequence, name, description, icon, journey_id)
SELECT
    gen_random_uuid(),
    s.sequence,
    s.name,
    s.description,
    s.icon,
    j.id
FROM new_journey j
JOIN (
    VALUES
        (1, 'Onboarding', 'Setup & Discovery','Building2'),
        (2, 'Talent Discovery', 'Find Rising Stars','Search'),
        (3, 'Community Investment', 'Sponsor & Fund','DollarSign'),
        (4, 'Branding', 'Build Reputation','Award'),
        (5, 'Hiring Outcomes', 'Measure Success','Briefcase')
) AS s(sequence, name, description, icon)
ON TRUE;

-- add milestones
WITH target_stage AS (
    SELECT s.id
    FROM stage s
    JOIN journey j ON s.journey_id = j.id
    WHERE s.name = 'Onboarding'
      AND j.name = 'Recruiter'
)
INSERT INTO milestone (id, sequence, name, description, stage_id)
SELECT
    gen_random_uuid(),
    m.sequence,
    m.name,
    m.description,
    ts.id
FROM target_stage ts
JOIN (
    VALUES
        (1, 'Create company & recruiter profiles',        'Create company & recruiter profiles'),
        (2, 'Select skill investment interests',          'Select skill investment interests'),
        (3, 'Get an overview of CV bank & talent pool',   'Get an overview of CV bank & talent pool')
) AS m(sequence, name, description)
ON TRUE;

WITH target_stage AS (
    SELECT s.id
    FROM stage s
    JOIN journey j ON s.journey_id = j.id
    WHERE s.name = 'Talent Discovery'
      AND j.name = 'Recruiter'
)
INSERT INTO milestone (id, sequence, name, description, stage_id)
SELECT
    gen_random_uuid(),
    m.sequence,
    m.name,
    m.description,
    ts.id
FROM target_stage ts
JOIN (
    VALUES
        (4, 'Explore CV bank with filters (skills, projects, certifications)', 'Explore CV bank with filters (skills, projects, certifications)'),
        (5, 'Use AI matching to find rising talent',                           'Use AI matching to find rising talent'),
        (6, 'Shortlist candidates & build pipelines',                          'Shortlist candidates & build pipelines')
) AS m(sequence, name, description)
ON TRUE;


WITH target_stage AS (
    SELECT s.id
    FROM stage s
    JOIN journey j ON s.journey_id = j.id
    WHERE s.name = 'Community Investment'
      AND j.name = 'Recruiter'
)
INSERT INTO milestone (id, sequence, name, description, stage_id)
SELECT
    gen_random_uuid(),
    m.sequence,
    m.name,
    m.description,
    ts.id
FROM target_stage ts
JOIN (
    VALUES
        (7,  'Sponsor incubators (AI, Cloud, Security, etc.)',      'Sponsor incubators (AI, Cloud, Security, etc.)'),
        (8,  'Fund hackathons and coding challenges',              'Fund hackathons and coding challenges'),
        (9,  'Submit skill investment/resource proposals',         'Submit skill investment/resource proposals'),
        (10, 'Arrange private hiring competitions',                'Arrange private hiring competitions')
) AS m(sequence, name, description)
ON TRUE;


WITH target_stage AS (
    SELECT s.id
    FROM stage s
    JOIN journey j ON s.journey_id = j.id
    WHERE s.name = 'Branding'
      AND j.name = 'Recruiter'
)
INSERT INTO milestone (id, sequence, name, description, stage_id)
SELECT
    gen_random_uuid(),
    m.sequence,
    m.name,
    m.description,
    ts.id
FROM target_stage ts
JOIN (
    VALUES
        (11, 'Host webinars, AMAs, and community events',   'Host webinars, AMAs, and community events'),
        (12, 'Gain visibility as a top recruiter',          'Gain visibility as a top recruiter'),
        (13, 'Share success stories of hires',              'Share success stories of hires')
) AS m(sequence, name, description)
ON TRUE;


WITH target_stage AS (
    SELECT s.id
    FROM stage s
    JOIN journey j ON s.journey_id = j.id
    WHERE s.name = 'Hiring Outcomes'
      AND j.name = 'Recruiter'
)
INSERT INTO milestone (id, sequence, name, description, stage_id)
SELECT
    gen_random_uuid(),
    m.sequence,
    m.name,
    m.description,
    ts.id
FROM target_stage ts
JOIN (
    VALUES
        (14, 'Hire top-performing developers',                           'Hire top-performing developers'),
        (15, 'Track ROI of sponsored programs',                          'Track ROI of sponsored programs'),
        (16, 'Establish long-term brand recognition in the community',   'Establish long-term brand recognition in the community')
) AS m(sequence, name, description)
ON TRUE;


-- //////////////////
-- Startup journey //
-- //////////////////

INSERT INTO journey (id, name, title, description)
VALUES (
    gen_random_uuid(),
    'Startup',
    'Build your MVP, access talent, and scale your product with community support through 5 growth stages.',
    'Build your MVP, access talent, and scale your product with community support and resources.'
);


WITH startup_journey AS (
    SELECT id
    FROM journey
    WHERE name = 'Startup'
)
INSERT INTO stage (id, sequence, name, description, icon, journey_id)
SELECT
    gen_random_uuid(),
    s.sequence,
    s.name,
    s.description,
    s.icon,
    j.id
FROM startup_journey j
JOIN (
    VALUES
        (1, 'Foundation',  'Onboarding & Discovery',    'Lightbulb'),
        (2, 'Discovery',   'Talent Discovery',          'UserPlus'),
        (3, 'Incubation',  'Product Development',       'Code'),
        (4, 'Growth',      'Community Integration',     'Network'),
        (5, 'Impact',      'Scale',                     'Zap')
) AS s(sequence, name, description, icon)
ON TRUE;


WITH target_stage AS (
    SELECT s.id
    FROM stage s
    JOIN journey j ON s.journey_id = j.id
    WHERE s.name = 'Foundation'
      AND j.name = 'Startup'
)
INSERT INTO milestone (id, sequence, name, description, stage_id)
SELECT gen_random_uuid(), m.sequence, m.name, m.description, ts.id
FROM target_stage ts
JOIN (
    VALUES
        (1, 'Register as a startup in the community', 'Register as a startup in the community'),
        (2, 'Create a profile with vision, product idea, and current challenges',
            'Create a profile with vision, product idea, and current challenges'),
        (3, 'Identify needed skills and technical gaps', 'Identify needed skills and technical gaps'),
        (4, 'Explore the developer CV bank and available talent pool',
            'Explore the developer CV bank and available talent pool')
) AS m(sequence, name, description)
ON TRUE;


WITH target_stage AS (
    SELECT s.id
    FROM stage s
    JOIN journey j ON s.journey_id = j.id
    WHERE s.name = 'Discovery'
      AND j.name = 'Startup'
)
INSERT INTO milestone (id, sequence, name, description, stage_id)
SELECT gen_random_uuid(), m.sequence, m.name, m.description, ts.id
FROM target_stage ts
JOIN (
    VALUES
        (5, 'Match with developers and mentors from the community',
            'Match with developers and mentors from the community'),
        (6, 'Build small project teams for MVPs and proofs-of-concept',
            'Build small project teams for MVPs and proofs-of-concept'),
        (7, 'Start collaborating on technical tasks and use cases',
            'Start collaborating on technical tasks and use cases')
) AS m(sequence, name, description)
ON TRUE;


WITH target_stage AS (
    SELECT s.id
    FROM stage s
    JOIN journey j ON s.journey_id = j.id
    WHERE s.name = 'Incubation'
      AND j.name = 'Startup'
)
INSERT INTO milestone (id, sequence, name, description, stage_id)
SELECT gen_random_uuid(), m.sequence, m.name, m.description, ts.id
FROM target_stage ts
JOIN (
    VALUES
        (8,  'Join the Startup Incubator Track', 'Join the Startup Incubator Track'),
        (9,  'Access infrastructure, labs, and architecture guidance',
             'Access infrastructure, labs, and architecture guidance'),
        (10, 'Run hackathons to test concepts and attract contributors',
             'Run hackathons to test concepts and attract contributors'),
        (11, 'Build and refine the first product (MVP)',
             'Build and refine the first product (MVP)')
) AS m(sequence, name, description)
ON TRUE;


WITH target_stage AS (
    SELECT s.id
    FROM stage s
    JOIN journey j ON s.journey_id = j.id
    WHERE s.name = 'Growth'
      AND j.name = 'Startup'
)
INSERT INTO milestone (id, sequence, name, description, stage_id)
SELECT gen_random_uuid(), m.sequence, m.name, m.description, ts.id
FROM target_stage ts
JOIN (
    VALUES
        (12, 'Open source selected parts of the project for community adoption',
             'Open source selected parts of the project for community adoption'),
        (13, 'Gain visibility through demo days, startup spotlight sessions, and blogs',
             'Gain visibility through demo days, startup spotlight sessions, and blogs'),
        (14, 'Partner with recruiters to scale the team with specialized talent',
             'Partner with recruiters to scale the team with specialized talent')
) AS m(sequence, name, description)
ON TRUE;


WITH target_stage AS (
    SELECT s.id
    FROM stage s
    JOIN journey j ON s.journey_id = j.id
    WHERE s.name = 'Impact'
      AND j.name = 'Startup'
)
INSERT INTO milestone (id, sequence, name, description, stage_id)
SELECT gen_random_uuid(), m.sequence, m.name, m.description, ts.id
FROM target_stage ts
JOIN (
    VALUES
        (15, 'Launch MVP to market with community support',
             'Launch MVP to market with community support'),
        (16, 'Pitch to investors via community pitch events',
             'Pitch to investors via community pitch events'),
        (17, 'Scale product and operations while maintaining talent pipeline',
             'Scale product and operations while maintaining talent pipeline')
) AS m(sequence, name, description)
ON TRUE;


--- //////////////////////
---       User         ///
--- //////////////////////

WITH target_domain AS (
    SELECT id
    FROM domain
    WHERE name = 'Software & Application Engineering'
),
target_stage AS (
    SELECT s.id
    FROM stage s
    JOIN journey j on j.id=s.journey_id
    WHERE s.name = 'Foundation'
    AND j.name = 'IT Professional'
),
target_profile AS (
    SELECT id
    FROM profile
    WHERE name = 'Frontend Developer'
)
INSERT INTO itprofessional (id, userid, domain_id, stage_id, profile_id)
SELECT
    gen_random_uuid(),
    'waelibrahim2000@hotmail.com',
    d.id,
    s.id,
    p.id
FROM target_domain d
JOIN target_stage s ON TRUE
JOIN target_profile p ON TRUE;

-- use other profiles
WITH target_user AS (
    SELECT id
    FROM itprofessional
    WHERE userid = 'waelibrahim2000@hotmail.com'
),
target_profiles AS (
    SELECT id
    FROM profile
    WHERE name IN ('Backend Developer', 'Full Stack Engineer')
)
INSERT INTO user_profiles (user_id, profile_id)
SELECT
    u.id,
    p.id
FROM target_user u
JOIN target_profiles p ON TRUE;

-- add user progress record

WITH target_user AS (
    SELECT id
    FROM itprofessional
    WHERE userid = 'waelibrahim2000@hotmail.com'
),
current_milestone AS (
    SELECT m.id
    FROM milestone m
    JOIN stage s ON m.stage_id = s.id
    JOIN journey j ON s.journey_id = j.id
    WHERE m.sequence = 1
      AND s.name = 'Foundation'
      AND j.name = 'IT Professional'
),
next_milestone AS (
    SELECT m.id
    FROM milestone m
    JOIN stage s ON m.stage_id = s.id
    JOIN journey j ON s.journey_id = j.id    
    WHERE m.sequence = 2
      AND s.name = 'Foundation'
      AND j.name = 'IT Professional'
)
INSERT INTO user_progress (
    id,
    user_id,
    milestone_id,
    nextmilestone_id,
    prevprogress_id,
    startdate,
    closedate,
    status,
    earned_points
)
SELECT
    gen_random_uuid(),
    u.id,
    cm.id,
    nm.id,
    NULL,
    CURRENT_DATE - INTERVAL '10 days',
    CURRENT_DATE - INTERVAL '8 days',
    'Completed',
    1
FROM target_user u
JOIN current_milestone cm ON TRUE
JOIN next_milestone nm ON TRUE;

-- add another user

WITH target_domain AS (
    SELECT id
    FROM domain
    WHERE name = 'Cloud, DevOps & Infrastructure'
),
target_stage AS (
    SELECT s.id
    FROM stage s
    JOIN journey j on j.id=s.journey_id
    WHERE s.name = 'Practice'
    AND j.name = 'IT Professional'
),
target_profile AS (
    SELECT id
    FROM profile
    WHERE name = 'Backend Developer'
)
INSERT INTO itprofessional (id, userid, domain_id, stage_id, profile_id)
SELECT
    gen_random_uuid(),
    'test@test.com',
    d.id,
    s.id,
    p.id
FROM target_domain d
JOIN target_stage s ON TRUE
JOIN target_profile p ON TRUE;

-- create Team
WITH admin_user AS (
    SELECT id
    FROM itprofessional
    WHERE userid = 'waelibrahim2000@hotmail.com'
)
INSERT INTO team (id, name, admin_user_id)
SELECT gen_random_uuid(), 'Alpha Team', u.id
FROM admin_user u;

-- add users to team
WITH target_team AS (
    SELECT id
    FROM team
    WHERE name = 'Alpha Team'
),
team_users AS (
    SELECT id
    FROM itprofessional
    WHERE userid IN ('waelibrahim2000@hotmail.com', 'test@test.com')
)
INSERT INTO team_members (team_id, user_id)
SELECT t.id, u.id
FROM target_team t
JOIN team_users u ON TRUE;

-- /////////////
-- badges     //
-- ////////////
insert into badge (id, name, title, icon, rarity, rule_type, rule_value) values (gen_random_uuid(),'Member','✅ Member', 'User', 'common', 'COMPLETE_PROFILE','*');
insert into badge (id, name, title, icon, rarity, rule_type, rule_value) values (gen_random_uuid(),'Learner','🟦 Learner','GraduationCap', 'epic','ENROLL_TO_TRACK','*');
insert into badge (id, name, title, icon, rarity, rule_type, rule_value) values (gen_random_uuid(),'Active Member','💬 Active Member', 'Activity', 'rare', 'COMPLETE_TRACK', '*');
insert into badge (id, name, title, icon, rarity, rule_type, rule_value) values (gen_random_uuid(),'Practitioner', '🟩 Practitioner', 'Wrench','epic', 'COMPLETE_LAB', 'CLP');
insert into badge (id, name, title, icon, rarity ) values (gen_random_uuid(),'Helper','🤝 Helper', 'MessageSquare','legendary');
insert into badge (id, name, title, icon, rarity ) values (gen_random_uuid(),'Mentor','🧑‍🏫 Mentor', 'Award', 'epic');
insert into badge (id, name, title, icon, rarity ) values (gen_random_uuid(),'Team Player','🤜🤛 Team Player', 'Users','rare');
insert into badge (id, name, title, icon ) values (gen_random_uuid(),'Contributor','🟧 Contributor','Code-fork');
insert into badge (id, name, title, icon ) values (gen_random_uuid(),'Leader','🟨 Leader','Crown');
insert into badge (id, name, title, icon ) values (gen_random_uuid(),'Streak Master','🔥 Streak Master','Flame');
insert into badge (id, name, title, icon ) values (gen_random_uuid(),'Fast Climber','🏅 Fast Climber','Zap');
insert into badge (id, name, title, icon ) values (gen_random_uuid(),'Super Contributor','🚀 Super Contributor','');
insert into badge (id, name, title, icon ) values (gen_random_uuid(),'Top 5% Performer','🌟 Top 5% Performer','');


WITH target_user AS (
    SELECT id
    FROM itprofessional
    WHERE userid = 'waelibrahim2000@hotmail.com'
),
target_badge AS (
    SELECT id
    FROM badge
    WHERE name = 'Member'
)
INSERT INTO user_badges (id, user_id, badge_id, earnedat)
SELECT gen_random_uuid(), u.id, b.id, DATE '2025-04-01'
FROM target_user u
JOIN target_badge b ON TRUE;
