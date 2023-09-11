package gov.va.sparkcql.pipeline.optimizer;

import gov.va.sparkcql.domain.Plan;
import gov.va.sparkcql.pipeline.Component;

import gov.va.sparkcql.domain.Plan;

/**
 * TODO: Implement rules based optimizer. Goal of the optimizer is establishing
 * a dependency graph between all retrieves and transitive constraints between
 * dependencies. For instance, if an Encounter is constrained to the measurement
 * period and those same dates are used in a medication lookup, propogate
 * constraints forward.
 *
 * Rules should be based on typical patterns but won't cover all possible
 * optimization scenarios. Ideally the rules are composable such that an
 * inferred constraint from one rule might unlock an optimization for another.
 *
 * Optimization Rule Ideas:
 * - Inner Join Constraint: Table A and Tabe B are INNER JOINED.
 * - Define Elimination: If a definition isn't used in any material
 * way, exclude its retrieval. You see this with certain libraries which are
 * included as a matter of convenience but not all definitions are used.
 * - Interval Range Constraint: Retrieve is filtered to a range of values e.g.
 * period.
 * - Range Add Constraint: Same as Range Constraint except a value is added
 * to the argument e.g. period.end + 6 months.
 * - Scalar In Constraint: Table is filtered by an attribute with an IN clause.
 * - Code/VS Constraint: Filtered by a value set or code(s).
 *
 * May need to document the general relationships between certain domains. For
 * instance, Encounter to MedicationAdministered is an Inner Join constraint
 * whereas Encounter to MedicationDispense is not.
 *
 * NOTE: Could query results be used to "train" these constraints?
 */
public interface Optimizer extends Component {
        
    public Plan optimize(Plan plan);
}