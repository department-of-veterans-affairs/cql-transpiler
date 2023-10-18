
-- define a: 1
CREATE OR REPLACE VIEW a AS (SELECT 1 _val);

-- define b: {a}
CREATE OR REPLACE VIEW b AS (SELECT collect_list(_val) AS _val FROM a);

-- define c: {1, a}
CREATE OR REPLACE VIEW c AS (SELECT collect_list(_val) AS _val FROM ((SELECT 1 _val) UNION ALL (SELECT * FROM a)));

-- define d: {foo: b, bar: c, baz: 1}
CREATE OR REPLACE VIEW d AS (SELECT struct(foo, bar, baz) AS _val FROM (SELECT _val AS foo FROM b), (SELECT _val AS bar FROM c), (SELECT _val AS baz FROM (SELECT 1 _val)))

-- define e: d.foo
CREATE OR REPLACE VIEW e AS (SELECT _val.foo FROM d AS _val);

-- define f: [Encounter]
CREATE OR REPLACE VIEW f AS (SELECT * FROM Encounter);

-- define g: {foo: f, bar: 1}
CREATE OR REPLACE VIEW g AS (SELECT struct(*) AS _val FROM (SELECT _val as foo FROM (SELECT collect_list(*) AS _val FROM (SELECT struct(*) FROM f))), (SELECT _val AS bar FROM (SELECT 1 _val)));

-- define h: g.bar
CREATE OR REPLACE VIEW h AS (SELECT _val.bar AS _val FROM g);