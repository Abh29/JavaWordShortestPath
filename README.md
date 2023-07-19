# JavaWordShortestPath

    This is a solution to this game:
    - given a list of words in some language (ex: Russian) with known alphabet, and given two words from 
        this list, can you find the shortest list of words between the two, where each step you can alter one 
        letter only from the word ?
    - for example:  with the dictionary provided in the Files folder:
        the distance between 'било' and 'било' is 0, the list sould contain a single element ['било']
        the distance between 'дать' and 'било' is 6, the list should contain ['дать', 'даль', 'паль', 'пиль', 'киль', 'кило', 'било']
        there is no path from 'ящур' to 'дача' as such the distance is -1

## The implementation
    - for this task I've adopted the BFS method, where I create an adjacency list at each step, the list initialy contains all possible adjacent words
        to a given word, then remove those which do not belong to the dictionnary.