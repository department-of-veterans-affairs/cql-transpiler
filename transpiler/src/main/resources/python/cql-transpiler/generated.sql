
-- define a: 1
CREATE OR REPLACE VIEW a AS SELECT 1 _val;

-- define b: {a}
CREATE OR REPLACE VIEW b AS SELECT collect_list(_val) FROM a

-- define c: {a, b}
CREATE OR REPLACE VIEW c AS SELECT collect_list(_val) FROM (a union b)