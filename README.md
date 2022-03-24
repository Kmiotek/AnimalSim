# AnimalSim

Project created during "Programowanie Obiektowe" class. Project uses Java Swing to visualise simulation of animals evolving different traits. JUnit is used for tests.

Aplication makes it possible to create a map of a chosen size. Map stores positions of Animals and plants in a dictionary mapping positions to sets of objects on the position. Grass is spawned randomly with intensity chosen by user. Map is divided into two regions: Jungle with higher grass spawn rate in the middle and step with lower grass spawn rate at the edges. Animals move away from predator, towards their prey and towards grass (if they are able to eat it). Animals reproduce when having eaten enough food; when reproducing a copy of animal is created with slightly different traits.

Traits that can be evolved by animals include:
- Size (bigger animal needs more food, but it can't be eaten by a predator smaller than itself)
- Speed (faster animals use up more energy while moving)
- Diet (Carnivore/Herbivore)

When creating the simulation user can modify
- Map size
- Grass spawn rate
- Initial animal energy (energy that each animal starts with)
- Number of starting animals, together with their size and speed
- Move efficiency (if higher then animals will need more energy to move)
- Animal vision range (from how far away can animals detect other animals and plants)
- Meat quality (multiplies energy gained by carnivores when eating an animal)
- Jungle/Step ratio

While working aplication saves data about animals, which can be displayed in aplication in series of graphs. These graphs include:
- Average move speed/time
- Average size/time
- Average population size/time (With separate graphs for herbivores and carnivores)
- Average lifespan of animals/time
- Average number of children of living animals/time

It is also possible to highlight specific animal by clicling on it. It enables additional statisticks for that animal:
- Date of death (if highlighted animal dies)
- Number of children
- Number of descendants (and number of living descendants)
- Descendant type (carnivores/herbivores, visualised with pie chart)

Simulation can be paused and resumed at any moment, and simulation speed can be adjusted (from 5 udpates fer second to 200).
Simulation state can be saved to file in JSON format.





Tutaj jest projekt w swojej obecnej formie, w branchach są wersje będące oodpowiedziami na zadania z poszczególnych tygodni.
