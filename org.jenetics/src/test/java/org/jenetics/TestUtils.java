/*
 * Java Genetic Algorithm Library (@__identifier__@).
 * Copyright (c) @__year__@ Franz Wilhelmstötter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author:
 *    Franz Wilhelmstötter (franz.wilhelmstoetter@gmx.at)
 */
package org.jenetics;

import java.io.Serializable;
import java.util.Random;
import java.util.function.Function;

import org.jenetics.util.Array;
import org.jenetics.util.ISeq;
import org.jenetics.util.RandomRegistry;

/**
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version <em>$Date: 2014-04-16 $</em>
 */
class TestUtils {

	private TestUtils() {
	}

	/**
	 * Data for alter count tests.
	 */
	public static Object[][] alterCountParameters() {
		return new Object[][] {
			//    ngenes,       nchromosomes     npopulation
			{ 1,   1,  100 },
			{ 5,   1,  100 },
			{ 80,  1,  100 },
			{ 1,   2,  100 },
			{ 5,   2,  100 },
			{ 80,  2,  100 },
			{ 1,   15, 100 },
			{ 5,   15, 100 },
			{ 80,  15, 100 },

			{ 1,   1,  150 },
			{ 5,   1,  150 },
			{ 80,  1,  150 },
			{ 1,   2,  150 },
			{ 5,   2,  150 },
			{ 80,  2,  150 },
			{ 1,   15, 150 },
			{ 5,   15, 150 },
			{ 80,  15, 150 },

			{ 1,   1,  500 },
			{ 5,   1,  500 },
			{ 80,  1,  500 },
			{ 1,   2,  500 },
			{ 5,   2,  500 },
			{ 80,  2,  500 },
			{ 1,   15, 500 },
			{ 5,   15, 500 },
			{ 80,  15, 500 }
		};
	}

	/**
	 * Data for alter probability tests.
	 */
	public static Object[][] alterProbabilityParameters() {
		return new Object[][] {
			//    ngenes,       nchromosomes     npopulation
			{ 20,   20,  20, 0.5 },
			{ 1,   1,  150, 0.15 },
			{ 5,   1,  150, 0.15 },
			{ 80,  1,  150, 0.15 },
			{ 1,   2,  150, 0.15 },
			{ 5,   2,  150, 0.15 },
			{ 80,  2,  150, 0.15 },
			{ 1,   15, 150, 0.15 },
			{ 5,   15, 150, 0.15 },
			{ 80,  15, 150, 0.15 },

			{ 1,   1,  150, 0.5 },
			{ 5,   1,  150, 0.5 },
			{ 80,  1,  150, 0.5 },
			{ 1,   2,  150, 0.5 },
			{ 5,   2,  150, 0.5 },
			{ 80,  2,  150, 0.5 },
			{ 1,   15, 150, 0.5 },
			{ 5,   15, 150, 0.5 },
			{ 80,  15, 150, 0.5 },

			{ 1,   1,  150, 0.85 },
			{ 5,   1,  150, 0.85 },
			{ 80,  1,  150, 0.85 },
			{ 1,   2,  150, 0.85 },
			{ 5,   2,  150, 0.85 },
			{ 80,  2,  150, 0.85 },
			{ 1,   15, 150, 0.85 },
			{ 5,   15, 150, 0.85 },
			{ 80,  15, 150, 0.85 }
		};
	}

	/**
	 *  Create a population of DoubleGenes
	 */
	public static final Population<DoubleGene, Double> newDoubleGenePopulation(
		final int ngenes,
		final int nchromosomes,
		final int npopulation
	) {
		final Array<DoubleChromosome> chromosomes =
			new Array<>(nchromosomes);

		for (int i = 0; i < nchromosomes; ++i) {
			chromosomes.set(i, DoubleChromosome.of(0, 10, ngenes));
		}

		final Genotype<DoubleGene> genotype = new Genotype<>(chromosomes.toISeq());
		final Population<DoubleGene, Double> population =
			new Population<>(npopulation);

		for (int i = 0; i < npopulation; ++i) {
			population.add(Phenotype.of(genotype.newInstance(), FF, 0).evaluate());
		}

		return population;
	}

	public static final Population<EnumGene<Double>, Double> newPermutationDoubleGenePopulation(
		final int ngenes,
		final int nchromosomes,
		final int npopulation
	) {
		final Random random = new Random(122343);
		final Array<Double> alleles = new Array<>(ngenes);
		for (int i = 0; i < ngenes; ++i) {
			alleles.set(i, random.nextDouble()*10);
		}
		final ISeq<Double> ialleles = alleles.toISeq();

		final Array<PermutationChromosome<Double>> chromosomes =
			new Array<>(nchromosomes);

		for (int i = 0; i < nchromosomes; ++i) {
			chromosomes.set(i, PermutationChromosome.of(ialleles));
		}

		final Genotype<EnumGene<Double>> genotype = new Genotype<>(chromosomes.toISeq());
		final Population<EnumGene<Double>, Double> population =
			new Population<>(npopulation);

		for (int i = 0; i < npopulation; ++i) {
			population.add(Phenotype.of(genotype.newInstance(), PFF, 0));
		}

		return population;
	}

	private static final Function<Genotype<EnumGene<Double>>, Double>
	PFF = new Function<Genotype<EnumGene<Double>>, Double>() {
		@Override
		public Double apply(Genotype<EnumGene<Double>> value) {
			return value.getGene().getAllele();
		}
	};

	/**
	 * Count the number of different genes.
	 */
	public static int diff (
		final Population<DoubleGene, Double> p1,
		final Population<DoubleGene, Double> p2
	) {
		int count = 0;
		for (int i = 0; i < p1.size(); ++i) {
			final Genotype<?> gt1 = p1.get(i).getGenotype();
			final Genotype<?> gt2 = p2.get(i).getGenotype();

			for (int j = 0; j < gt1.length(); ++j) {
				final Chromosome<?> c1 = gt1.getChromosome(j);
				final Chromosome<?> c2 = gt2.getChromosome(j);

				for (int k = 0; k < c1.length(); ++k) {
					if (!c1.getGene(k).equals(c2.getGene(k))) {
						++count;
					}
				}
			}
		}
		return count;
	}

	/**
	 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
	 * @version $Id$
	 */
	private static final class Continous
		implements Function<Genotype<DoubleGene>, Double>,
					Serializable
	{
		private static final long serialVersionUID = 1L;

		@Override
		public Double apply(Genotype<DoubleGene> genotype) {
			return genotype.getChromosome().getGene().getAllele();
		}
	}

	/**
	 * 'Identity' fitness function.
	 */
	public static final Function<Genotype<DoubleGene>, Double> FF = new Continous();

	public static GeneticAlgorithm<DoubleGene, Double> GA() {
		return new GeneticAlgorithm<>(
				Genotype.of(DoubleChromosome.of(0, 1)), FF
			);
	}


	public static Phenotype<DoubleGene, Double> newDoublePhenotype(final double value) {
		return Phenotype.of(Genotype.of(
			DoubleChromosome.of(DoubleGene.of(value, 0, 10))), FF, 0
		).evaluate();
	}

	public static Phenotype<DoubleGene, Double> newDoublePhenotype() {
		final Random random = RandomRegistry.getRandom();
		return newDoublePhenotype(random.nextDouble()*10);
	}

	public static Population<DoubleGene, Double> newDoublePopulation(final int length) {
		final Population<DoubleGene, Double> population =
			new Population<>(length);

		for (int i = 0; i < length; ++i) {
			population.add(newDoublePhenotype());
		}

		return population;
	}

}
