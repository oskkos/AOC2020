package fi.k6.aoc2020

/*
--- Day 1: Report Repair ---
After saving Christmas five years in a row, you've decided to take a vacation at a nice resort on a tropical island. Surely, Christmas will go on without you.

The tropical island has its own currency and is entirely cash-only. The gold coins used there have a little picture of a starfish; the locals just call them stars. None of the currency exchanges seem to have heard of them, but somehow, you'll need to find fifty of these coins by the time you arrive so you can pay the deposit on your room.

To save your vacation, you need to get all fifty stars by December 25th.

Collect stars by solving puzzles. Two puzzles will be made available on each day in the Advent calendar; the second puzzle is unlocked when you complete the first. Each puzzle grants one star. Good luck!

Before you leave, the Elves in accounting just need you to fix your expense report (your puzzle input); apparently, something isn't quite adding up.

Specifically, they need you to find the two entries that sum to 2020 and then multiply those two numbers together.

For example, suppose your expense report contained the following:

1721
979
366
299
675
1456
In this list, the two entries that sum to 2020 are 1721 and 299. Multiplying them together produces 1721 * 299 = 514579, so the correct answer is 514579.

Of course, your expense report is much larger. Find the two entries that sum to 2020; what do you get if you multiply them together?
*/
fun day0101(expenses: List<Int>): Int {
    for (i in 0..expenses.lastIndex) {
        val a = expenses.get(i)
        for (j in i..expenses.lastIndex) {
            val b = expenses.get(j)
            if ((a + b) == 2020) return a * b
        }
    }
    throw error("Nothing found")
}

/*
--- Part Two ---
The Elves in accounting are thankful for your help; one of them even offers you a starfish coin they had left over from a past vacation. They offer you a second one if you can find three numbers in your expense report that meet the same criteria.

Using the above example again, the three entries that sum to 2020 are 979, 366, and 675. Multiplying them together produces the answer, 241861950.

In your expense report, what is the product of the three entries that sum to 2020?
 */
fun day0102(expenses: List<Int>): Int {
    for (i in 0..expenses.lastIndex) {
        val a = expenses.get(i)
        for (j in i..expenses.lastIndex) {
            val b = expenses.get(j)
            for (k in j..expenses.lastIndex) {
                val c = expenses.get(k)
                if ((a + b + c) == 2020) return a * b * c
            }
        }
    }
    throw error("Nothing found")
}
fun main() {
    println("--- Day 1: Report Repair ---")
    println("Part 1: " + day0101(expenses()))
    println("Part 2: " + day0102(expenses()))
}

fun expenses(): List<Int> {
    val input =
        """1825
        1944
        1802
        1676
        1921
        1652
        1710
        1952
        1932
        1934
        1823
        1732
        1795
        1681
        1706
        1697
        1919
        1695
        2007
        1889
        1942
        961
        1868
        1878
        1723
        416
        1875
        1831
        1890
        1654
        1956
        1827
        973
        1947
        1688
        1680
        1808
        1998
        1794
        1552
        1935
        1693
        1824
        1711
        1766
        1668
        1968
        1884
        217
        2003
        1869
        1658
        1953
        1829
        1984
        2005
        1973
        428
        1957
        1925
        1719
        1797
        321
        1804
        1971
        922
        1976
        1863
        2008
        1806
        1833
        1809
        1707
        1954
        1811
        1815
        1915
        1799
        1917
        1664
        1937
        1775
        1685
        1756
        1940
        1660
        1859
        1916
        1989
        1763
        1994
        1716
        1689
        1866
        1708
        1670
        1982
        1870
        1847
        1627
        1819
        1786
        1828
        1640
        1699
        1722
        1737
        1882
        1666
        1871
        1703
        1770
        1623
        1837
        1636
        1655
        1930
        1739
        1810
        1805
        1861
        1922
        1993
        1896
        1760
        2002
        1779
        1633
        1972
        1856
        1641
        1718
        2004
        1730
        1826
        1923
        1753
        1735
        660
        1988
        1796
        1990
        1720
        1626
        1788
        1700
        942
        1902
        1943
        1758
        1839
        1924
        938
        1634
        1724
        1983
        1683
        1687
        1904
        1907
        1757
        2001
        1910
        1849
        1781
        1981
        1743
        1851
        2009
        619
        1898
        1891
        1751
        1765
        1959
        1888
        1894
        1759
        389
        1964
        1900
        1742
        1672
        1969
        1978
        1933
        1906
        1807
        1867
        1838
        1960
        1814
        1950
        1918
        1726
        1986
        1746
        2006
        1949
        1784"""
    return input.lines().map({  it.trim().toInt() })
}
