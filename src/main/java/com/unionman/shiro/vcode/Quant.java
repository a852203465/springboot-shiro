package com.unionman.shiro.vcode;

@SuppressWarnings("ALL")
public class Quant {

    /** number of colours used */
    private static final int NET_SIZE = 256;
    
    /* four primes near 500 - assume no image has a length so large */
    /** that it is divisible by all four primes */
    private static final int PRIME_1 = 499;
    private static final int PRIME_2 = 491;
    private static final int PRIME_3 = 487;
    private static final int PRIME_4 = 503;

    private static final int MIN_PICTURE_BYTES = (3 * PRIME_4);

    /* minimum size for input image */

	/* Program Skeleton
	   ----------------
	   [select samplefac in range 1..30]
	   [read image from input file]
	   pic = (unsigned char*) malloc(3*width*height);
	   initnet(pic,3*width*height,samplefac);
	   learn();
	   unbiasnet();
	   [write output image header, using writecolourmap(f)]
	   inxbuild();
	   write output image using inxsearch(b,g,r)      */

	/** Network Definitions*/
    private static final int MAX_NET_POS = (NET_SIZE - 1);

    /** bias for colour values */
    private static final int NET_BIASSHIFT = 4;

    /** no. of learning cycles */
    private static final int NCYCLES = 100;

    /** defs for freq and bias */
    private static final int INT_BIA_SSHIFT = 16;
    private static final int INT_BIAS = (((int) 1) << INT_BIA_SSHIFT);

    /** GAMMA = 1024 */
    private static final int GAMMA_SHIFT = 10;
    private static final int GAMMA = (((int) 1) << GAMMA_SHIFT);
    private static final int BETA_SHIFT = 10;

    /** BETA = 1/1024 */
    private static final int BETA = (INT_BIAS >> BETA_SHIFT);
    private static final int BETA_GAMMA = (INT_BIAS << (GAMMA_SHIFT - BETA_SHIFT));

    /* defs for decreasing radius factor */

    /** for 256 cols, radius starts */
    private static final int INIT_RAD = (NET_SIZE >> 3);

    /** at 32.0 biased by 6 bits */
    private static final int RADIUSBIASSHIFT = 6;
    private static final int RADIUSBIAS = (((int) 1) << RADIUSBIASSHIFT);

    /** and decreases by a */
    private static final int INITRADIUS = (INIT_RAD * RADIUSBIAS);

    /** factor of 1/30 each cycle */
    private static final int RADIUSDEC = 30;

    /* defs for decreasing alpha factor */

    /** alpha starts at 1.0 */
    private static final int ALPHABIASSHIFT = 10;
    private static final int INIT_ALPHA = (((int) 1) << ALPHABIASSHIFT);

    /*** biased by 10 bits */
    private int alphadec;

    /** RADBIAS and ALPHARADBIAS used for radpower calculation */
    private static final int RADBIASSHIFT = 8;
    private static final int RADBIAS = (((int) 1) << RADBIASSHIFT);
    private static final int ALPHARADBSHIFT = (ALPHABIASSHIFT + RADBIASSHIFT);
    private static final int ALPHARADBIAS = (((int) 1) << ALPHARADBSHIFT);

	/* Types and Global Variables*/

    /** the input image itself */
    private byte[] thepicture;

    /** lengthcount = H*W*3 */
    private int lengthcount;

    /** sampling factor 1..30 */
    private int samplefac;

    //   typedef int pixel[4];

    /* BGRc */

    /** the network itself - [netsize][4] */
    private int[][] network;

    private int[] netindex = new int[256];

    /** for network lookup - really 256 */
    private int[] bias = new int[NET_SIZE];

    /** bias and freq arrays for learning */
    private int[] freq = new int[NET_SIZE];
    private int[] radpower = new int[INIT_RAD];

    /*radpower for precomputation */

    /** Initialise network in range (0,0,0) to (255,255,255) and set parameters
       ----------------------------------------------------------------------- */
    public Quant(byte[] thepic, int len, int sample) {

        int i;
        int[] p;

        thepicture = thepic;
        lengthcount = len;
        samplefac = sample;

        network = new int[NET_SIZE][];
        for (i = 0; i < NET_SIZE; i++) {
            network[i] = new int[4];
            p = network[i];
            p[0] = p[1] = p[2] = (i << (NET_BIASSHIFT + 8)) / NET_SIZE;

            /** 1/netsize */
            freq[i] = INT_BIAS / NET_SIZE;
            bias[i] = 0;
        }
    }

    public byte[] colorMap() {
        byte[] map = new byte[3 * NET_SIZE];
        int[] index = new int[NET_SIZE];
        for (int i = 0; i < NET_SIZE; i++) {
            index[network[i][3]] = i;
        }
        int k = 0;
        for (int i = 0; i < NET_SIZE; i++) {
            int j = index[i];
            map[k++] = (byte) (network[j][0]);
            map[k++] = (byte) (network[j][1]);
            map[k++] = (byte) (network[j][2]);
        }
        return map;
    }

    /** Insertion sort of network and building of netindex[0..255] (to do after unbias) */
    public void inxbuild() {

        int i, j, smallpos, smallval;
        int[] p;
        int[] q;
        int previouscol, startpos;

        previouscol = 0;
        startpos = 0;
        for (i = 0; i < NET_SIZE; i++) {
            p = network[i];
            smallpos = i;

            /** index on g */
            smallval = p[1];

            /** find smallest in i..netsize-1 */
            for (j = i + 1; j < NET_SIZE; j++) {
                q = network[j];
                if (q[1] < smallval) {
                    smallpos = j;
                    smallval = q[1];
                }
            }
            q = network[smallpos];

            /** swap p (i) and q (smallpos) entries */
            if (i != smallpos) {
                j = q[0];
                q[0] = p[0];
                p[0] = j;
                j = q[1];
                q[1] = p[1];
                p[1] = j;
                j = q[2];
                q[2] = p[2];
                p[2] = j;
                j = q[3];
                q[3] = p[3];
                p[3] = j;
            }
            /** smallval entry is now in position i */
            if (smallval != previouscol) {
                netindex[previouscol] = (startpos + i) >> 1;
                for (j = previouscol + 1; j < smallval; j++) {
                    netindex[j] = i;
                }
                previouscol = smallval;
                startpos = i;
            }
        }
        netindex[previouscol] = (startpos + MAX_NET_POS) >> 1;
        for (j = previouscol + 1; j < 256; j++) {

            /** really 256 */
            netindex[j] = MAX_NET_POS;
        }
    }

    /** Main Learning Loop
       ------------------ */
    public void learn() {

        int i, j, b, g, r;
        int radius, rad, alpha, step, delta, samplepixels;
        byte[] p;
        int pix, lim;

        if (lengthcount < MIN_PICTURE_BYTES) {
            samplefac = 1;
        }
        alphadec = 30 + ((samplefac - 1) / 3);
        p = thepicture;
        pix = 0;
        lim = lengthcount;
        samplepixels = lengthcount / (3 * samplefac);
        delta = samplepixels / NCYCLES;
        alpha = INIT_ALPHA;
        radius = INITRADIUS;

        rad = radius >> RADIUSBIASSHIFT;
        if (rad <= 1) {
            rad = 0;
        }
        for (i = 0; i < rad; i++) {
            radpower[i] =
                    alpha * (((rad * rad - i * i) * RADBIAS) / (rad * rad));
        }

        if (lengthcount < MIN_PICTURE_BYTES) {
            step = 3;
        } else if ((lengthcount % PRIME_1) != 0) {
            step = 3 * PRIME_1;
        } else {
            if ((lengthcount % PRIME_2) != 0) {
                step = 3 * PRIME_2;
            } else {
                if ((lengthcount % PRIME_3) != 0) {
                    step = 3 * PRIME_3;
                } else {
                    step = 3 * PRIME_4;
                }
            }
        }

        i = 0;
        while (i < samplepixels) {
            b = (p[pix + 0] & 0xff) << NET_BIASSHIFT;
            g = (p[pix + 1] & 0xff) << NET_BIASSHIFT;
            r = (p[pix + 2] & 0xff) << NET_BIASSHIFT;
            j = contest(b, g, r);

            altersingle(alpha, j, b, g, r);
            if (rad != 0) {

                /** alter neighbours */
                alterneigh(rad, j, b, g, r);
            }

            pix += step;
            if (pix >= lim) {
                pix -= lengthcount;
            }

            i++;
            if (delta == 0) {
                delta = 1;
            }
            if (i % delta == 0) {
                alpha -= alpha / alphadec;
                radius -= radius / RADIUSDEC;
                rad = radius >> RADIUSBIASSHIFT;
                if (rad <= 1) {
                    rad = 0;
                }
                for (j = 0; j < rad; j++) {
                    radpower[j] =
                            alpha * (((rad * rad - j * j) * RADBIAS) / (rad * rad));
                }
            }
        }
        //fprintf(stderr,"finished 1D learning: final alpha=%f !\n",((float)alpha)/INIT_ALPHA);
    }

    /** Search for BGR values 0..255 (after net is unbiased) and return colour index */
    public int map(int b, int g, int r) {

        int i, j, dist, a, bestd;
        int[] p;
        int best;

        /** biggest possible dist is 256*3 */
        bestd = 1000;
        best = -1;
        i = netindex[g];

        /** start at netindex[g] and work outwards */
        j = i - 1;

        while ((i < NET_SIZE) || (j >= 0)) {
            if (i < NET_SIZE) {
                p = network[i];

                /** inx key */
                dist = p[1] - g;
                if (dist >= bestd) {

                    /** stop iter */
                    i = NET_SIZE;
                } else {
                    i++;
                    if (dist < 0) {
                        dist = -dist;
                    }
                    a = p[0] - b;
                    if (a < 0) {
                        a = -a;
                    }
                    dist += a;
                    if (dist < bestd) {
                        a = p[2] - r;
                        if (a < 0) {
                            a = -a;
                        }
                        dist += a;
                        if (dist < bestd) {
                            bestd = dist;
                            best = p[3];
                        }
                    }
                }
            }
            if (j >= 0) {
                p = network[j];

                /** inx key - reverse dif */
                dist = g - p[1];
                if (dist >= bestd) {

                    /** stop iter */
                    j = -1;
                } else {
                    j--;
                    if (dist < 0) {
                        dist = -dist;
                    }
                    a = p[0] - b;
                    if (a < 0) {
                        a = -a;
                    }
                    dist += a;
                    if (dist < bestd) {
                        a = p[2] - r;
                        if (a < 0) {
                            a = -a;
                        }
                        dist += a;
                        if (dist < bestd) {
                            bestd = dist;
                            best = p[3];
                        }
                    }
                }
            }
        }
        return (best);
    }

    public byte[] process() {
        learn();
        unbiasnet();
        inxbuild();
        return colorMap();
    }

    /** Unbias network to give byte values 0..255 and record position i to prepare for sort */
    public void unbiasnet() {

        int i;// j;

        for (i = 0; i < NET_SIZE; i++) {
            network[i][0] >>= NET_BIASSHIFT;
            network[i][1] >>= NET_BIASSHIFT;
            network[i][2] >>= NET_BIASSHIFT;
            network[i][3] = i; /** record colour no */
        }
    }

    /** Move adjacent neurons by precomputed alpha*(1-((i-j)^2/[r]^2)) in radpower[|i-j|]*/
    private void alterneigh(int rad, int i, int b, int g, int r) {

        int j, k, lo, hi, a, m;
        int[] p;

        lo = i - rad;
        if (lo < -1) {
            lo = -1;
        }
        hi = i + rad;
        if (hi > NET_SIZE) {
            hi = NET_SIZE;
        }

        j = i + 1;
        k = i - 1;
        m = 1;
        while ((j < hi) || (k > lo)) {
            a = radpower[m++];
            if (j < hi) {
                p = network[j++];
                try {
                    p[0] -= (a * (p[0] - b)) / ALPHARADBIAS;
                    p[1] -= (a * (p[1] - g)) / ALPHARADBIAS;
                    p[2] -= (a * (p[2] - r)) / ALPHARADBIAS;
                } catch (Exception e) {
                    // prevents 1.3 miscompilation
                }
            }
            if (k > lo) {
                p = network[k--];
                try {
                    p[0] -= (a * (p[0] - b)) / ALPHARADBIAS;
                    p[1] -= (a * (p[1] - g)) / ALPHARADBIAS;
                    p[2] -= (a * (p[2] - r)) / ALPHARADBIAS;
                } catch (Exception e) {
                }
            }
        }
    }

    /** Move neuron i towards biased (b,g,r) by factor alpha */
    private void altersingle(int alpha, int i, int b, int g, int r) {

        /** alter hit neuron */
        int[] n = network[i];
        n[0] -= (alpha * (n[0] - b)) / INIT_ALPHA;
        n[1] -= (alpha * (n[1] - g)) / INIT_ALPHA;
        n[2] -= (alpha * (n[2] - r)) / INIT_ALPHA;
    }

    /** Search for biased BGR values
       ---------------------------- */
    private int contest(int b, int g, int r) {

        /** finds closest neuron (min dist) and updates freq */
        /** finds best neuron (min dist-bias) and returns position */
        /** for frequently chosen neurons, freq[i] is high and bias[i] is negative */
        /** bias[i] = GAMMA*((1/netsize)-freq[i]) */

        int i, dist, a, biasdist, betafreq;
        int bestpos, bestbiaspos, bestd, bestbiasd;
        int[] n;

        bestd = ~(((int) 1) << 31);
        bestbiasd = bestd;
        bestpos = -1;
        bestbiaspos = bestpos;

        for (i = 0; i < NET_SIZE; i++) {
            n = network[i];
            dist = n[0] - b;
            if (dist < 0) {
                dist = -dist;
            }
            a = n[1] - g;
            if (a < 0) {
                a = -a;
            }
            dist += a;
            a = n[2] - r;
            if (a < 0) {
                a = -a;
            }
            dist += a;
            if (dist < bestd) {
                bestd = dist;
                bestpos = i;
            }
            biasdist = dist - ((bias[i]) >> (INT_BIA_SSHIFT - NET_BIASSHIFT));
            if (biasdist < bestbiasd) {
                bestbiasd = biasdist;
                bestbiaspos = i;
            }
            betafreq = (freq[i] >> BETA_SHIFT);
            freq[i] -= betafreq;
            bias[i] += (betafreq << GAMMA_SHIFT);
        }
        freq[bestpos] += BETA;
        bias[bestpos] -= BETA_GAMMA;
        return (bestbiaspos);
    }
}
