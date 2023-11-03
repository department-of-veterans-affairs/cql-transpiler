-- define varname: something
CREATE OR REPLACE VIEW varname AS (something)

--  1
SELECT 1 _val;

-- define a: 1
-- define b: {a}
CREATE OR REPLACE VIEW a AS (SELECT 1 _val)
CREATE OR REPLACE VIEW b AS (SELECT collect_list(_val) AS _val FROM a);

-- define c: {1, a}
CREATE OR REPLACE VIEW c AS (SELECT collect_list(_val) AS _val FROM ((SELECT 1 _val) UNION ALL (SELECT * FROM a)));

-- define d: {foo: b, bar: c, baz: 1}
CREATE OR REPLACE VIEW d AS (SELECT struct(foo, bar, baz) AS _val FROM (SELECT _val AS foo FROM b), (SELECT _val AS bar FROM c), (SELECT _val AS baz FROM (SELECT 1 _val)))

-- d.foo
SELECT _val.foo FROM d AS _val;

-- define e: {foo: [Encounter], bar: 1}
-- (automatic compression happens)
CREATE OR REPLACE VIEW e AS (SELECT struct(*) AS _val FROM (SELECT _val as foo FROM (SELECT collect_list(*) AS _val FROM (SELECT struct(*) FROM [Encounter]))), (SELECT _val AS bar FROM (SELECT 1 _val)));

-- define f: e.bar
CREATE OR REPLACE VIEW f AS (SELECT _val.bar AS _val FROM g);

-- define g: e.foo
-- (automatic decompression happens)
CREATE OR REPLACE VIEW g AS (SELECT col.* FROM (SELECT explode(*) FROM (SELECT _val.foo AS _val FROM e)));

-- 2 = 3
-- 2 + 3
-- 2 - 3
-- 2 * 3
SELECT (SELECT 2 _val) = (SELECT 3 _val) _val;
SELECT (SELECT 2 _val) + (SELECT 3 _val) _val;
SELECT (SELECT 2 _val) - (SELECT 3 _val) _val;
SELECT (SELECT 2 _val) * (SELECT 3 _val) _val;

-- 2 / 3
SELECT (SELECT 0.0 + _val AS _val FROM (SELECT 2 _val)) / (SELECT 0.0 + _val AS _val FROM (SELECT 3 _val)) _val

-- -1
SELECT -1 * _val AS _val FROM (SELECT 1 _val);

-- (1 + 2 / -3 + 4) * 5
SELECT (SELECT (SELECT (SELECT 0.0 + _val AS _val FROM (SELECT 1 _val)) + (SELECT (SELECT 0.0 + _val AS _val FROM (SELECT 2 _val)) / (SELECT 0.0 + _val AS _val FROM (SELECT -1 * _val AS _val FROM (SELECT 3 _val))) _val) _val) + (SELECT 0.0 + _val AS _val FROM (SELECT 4 _val)) _val) * (SELECT 0.0 + _val AS _val FROM (SELECT 5 _val)) _val;

-- '1' + '2'
SELECT concat(leftval, rightval) AS _val FROM ((SELECT _val AS leftval FROM SELECT '1' _val) OUTER JOIN ((SELECT _val AS rightval FROM SELECT '1' _val)));

-- [Encounter]
SELECT * FROM Encounter;

-- [Encounter] E return E.period
SELECT E.period FROM ((SELECT * FROM Encounter) AS E);

-- [Encounter] E where E.period.end > 2011-1-1
SELECT * FROM ((SELECT * FROM Encounter) AS E) WHERE ((E.period.end) > (2011-1-1));

-- [Encounter] E return E.status.value
SELECT E.status.value FROM ((SELECT * FROM Encounter) AS E);

-- [Encounter] E where E.status.value = 'finished'
SELECT * FROM (Encounter AS E) WHERE (SELECT (E.status.value) = (SELECT 'finished' _val) _val);