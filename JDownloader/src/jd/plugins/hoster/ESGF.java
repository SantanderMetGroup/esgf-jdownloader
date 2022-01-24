package jd.plugins.hoster;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.appwork.utils.parser.UrlQuery;
import org.jdownloader.plugins.controller.host.LazyHostPlugin.FEATURE;

import jd.PluginWrapper;
import jd.http.Cookies;
import jd.http.requests.HeadRequest;
import jd.nutils.encoding.Encoding;
import jd.plugins.Account;
import jd.plugins.AccountInfo;
import jd.plugins.DownloadLink;
import jd.plugins.DownloadLink.AvailableStatus;
import jd.plugins.HostPlugin;
import jd.plugins.PluginForHost;

@HostPlugin(revision = "$Revision: 45328 $", interfaceVersion = 3, names = { "ESGF", "145.100.59.180.surf-hosted.nl", "acdisc.gesdisc.eosdis.nasa.gov", "aims3.llnl.gov", "cmip.fio.org.cn", "cordexesg.dmi.dk", "crd-esgf-drc.ec.gc.ca", "data.meteo.unican.es", "dataserver.nccs.nasa.gov", "dpesgf03.nccs.nasa.gov", "eridanus.eoc.dlr.de", "esg-cccr.tropmet.res.in", "esg-dn1.nsc.liu.se", "esg-dn1.ru.ac.th", "esg.lasg.ac.cn", "esg.pik-potsdam.de", "esg1.umr-cnrm.fr", "esgf-data.ucar.edu", "esgf-data1.ceda.ac.uk", "esgf-data1.diasjp.net", "esgf-data1.llnl.gov", "esgf-data2.ceda.ac.uk", "esgf-data2.llnl.gov", "esgf-data3.ceda.ac.uk", "esgf-dev.bsc.es", "esgf-ictp.hpc.cineca.it", "esgf-nimscmip6.apcc21.org", "esgf-node.cmcc.it", "esgf.anl.gov", "esgf.apcc21.org", "esgf.dwd.de", "esgf.ichec.ie", "esgf.knmi.nl", "esgf.nccs.nasa.gov", "esgf.nci.org.au", "esgf1.dkrz.de", "gpm1.gesdisc.eosdis.nasa.gov",
        "measures.gesdisc.eosdis.nasa.gov", "noresg.nird.sigma2.no", "vesg.ipsl.upmc.fr" }, urls = { "https://esgf.llnl.gov", "https?://145.100.59.180.surf-hosted.nl/.+", "https?://acdisc.gesdisc.eosdis.nasa.gov/.+", "https?://aims3.llnl.gov/.+", "https?://cmip.fio.org.cn/.+", "https?://cordexesg.dmi.dk/.+", "https?://crd-esgf-drc.ec.gc.ca/.+", "https?://data.meteo.unican.es/.+", "https?://dataserver.nccs.nasa.gov/.+", "https?://dpesgf03.nccs.nasa.gov/.+", "https?://eridanus.eoc.dlr.de/.+", "https?://esg-cccr.tropmet.res.in/.+", "https?://esg-dn1.nsc.liu.se/.+", "https?://esg-dn1.ru.ac.th/.+", "https?://esg.lasg.ac.cn/.+", "https?://esg.pik-potsdam.de/.+", "https?://esg1.umr-cnrm.fr/.+", "https?://esgf-data.ucar.edu/.+", "https?://esgf-data1.ceda.ac.uk/.+", "https?://esgf-data1.diasjp.net/.+", "https?://esgf-data1.llnl.gov/.+", "https?://esgf-data2.ceda.ac.uk/.+",
                "https?://esgf-data2.llnl.gov/.+", "https?://esgf-data3.ceda.ac.uk/.+", "https?://esgf-dev.bsc.es/.+", "https?://esgf-ictp.hpc.cineca.it/.+", "https?://esgf-nimscmip6.apcc21.org/.+", "https?://esgf-node.cmcc.it/.+", "https?://esgf.anl.gov/.+", "https?://esgf.apcc21.org/.+", "https?://esgf.dwd.de/.+", "https?://esgf.ichec.ie/.+", "https?://esgf.knmi.nl/.+", "https?://esgf.nccs.nasa.gov/.+", "https?://esgf.nci.org.au/.+", "https?://esgf1.dkrz.de/.+", "https?://gpm1.gesdisc.eosdis.nasa.gov/.+", "https?://measures.gesdisc.eosdis.nasa.gov/.+", "https?://noresg.nird.sigma2.no/.+", "https?://vesg.ipsl.upmc.fr/.+" })
public class ESGF extends PluginForHost {
    private static final String[] DATANODES = { "145.100.59.180.surf-hosted.nl", "acdisc.gesdisc.eosdis.nasa.gov", "aims3.llnl.gov", "cmip.fio.org.cn", "cordexesg.dmi.dk", "crd-esgf-drc.ec.gc.ca", "data.meteo.unican.es", "dataserver.nccs.nasa.gov", "dpesgf03.nccs.nasa.gov", "eridanus.eoc.dlr.de", "esg-cccr.tropmet.res.in", "esg-dn1.nsc.liu.se", "esg-dn1.ru.ac.th", "esg.lasg.ac.cn", "esg.pik-potsdam.de", "esg1.umr-cnrm.fr", "esgf-data.ucar.edu", "esgf-data1.ceda.ac.uk", "esgf-data1.diasjp.net", "esgf-data1.llnl.gov", "esgf-data2.ceda.ac.uk", "esgf-data2.llnl.gov", "esgf-data3.ceda.ac.uk", "esgf-dev.bsc.es", "esgf-ictp.hpc.cineca.it", "esgf-nimscmip6.apcc21.org", "esgf-node.cmcc.it", "esgf.anl.gov", "esgf.apcc21.org", "esgf.dwd.de", "esgf.ichec.ie", "esgf.knmi.nl", "esgf.nccs.nasa.gov", "esgf.nci.org.au", "esgf1.dkrz.de", "gpm1.gesdisc.eosdis.nasa.gov", "measures.gesdisc.eosdis.nasa.gov",
            "noresg.nird.sigma2.no", "vesg.ipsl.upmc.fr" };

    public ESGF(PluginWrapper wrapper) {
        super(wrapper);
        this.enablePremium();
    }

    @Override
    public AccountInfo fetchAccountInfo(final Account account) throws Exception {
        final AccountInfo ai = new AccountInfo();
        synchronized (ai) {
            ai.setUnlimitedTraffic();
            ai.setStatus("Premium account");
            // get hostnames for multihost
            List<String> hostnames = new ArrayList<String>();
            for (String hostname : DATANODES) {
                hostnames.add(hostname);
            }
            ai.setMultiHostSupport(this, hostnames);
            return ai;
        }
    }

    @Override
    public FEATURE[] getFeatures() {
        return new FEATURE[] { FEATURE.MULTIHOST };
    }

    @Override
    public void handleMultiHost(final DownloadLink link, final Account account) throws Exception {
        /*
         * fetchAccountInfo must fill ai.setMultiHostSupport to signal all supported multiHosts
         *
         * please synchronized on accountinfo and the ArrayList<String> when you change something in the handleMultiHost function
         *
         * in fetchAccountInfo we don't have to synchronize because we create a new instance of AccountInfo and fill it
         *
         * if you need customizable maxDownloads, please use getMaxSimultanDownload to handle this you are in multihost when account host
         * does not equal link host!
         *
         *
         *
         * will update this doc about error handling
         */
        String dllink = link.getPluginPatternMatcher();
        // br.getPage(dllink);
        HeadRequest request = br.createHeadRequest(dllink);
        br.getPage(request);
        if (br.getHttpConnection().getResponseCode() == 302 && br.getRedirectLocation().contains("/esg-orp/")) {
            handlePremium(link, account);
        } else {
            handleFree(link);
        }
    }

    @Override
    public String getAGBLink() {
        return "https://esgf.llnl.gov/";
    }

    @Override
    public AvailableStatus requestFileInformation(DownloadLink link) throws Exception {
        return AvailableStatus.TRUE;
    }

    @Override
    public void handleFree(DownloadLink link) throws Exception {
        requestFileInformation(link);
        dl = jd.plugins.BrowserAdapter.openDownload(br, link, "");
        dl.startDownload();
    }

    @Override
    public int getMaxSimultanPremiumDownloadNum() {
        return -1;
    }

    @Override
    public void reset() {
    }

    @Override
    public int getMaxSimultanFreeDownloadNum() {
        return -1;
    }

    @Override
    public void resetDownloadlink(DownloadLink link) {
    }

    @Override
    public void handlePremium(final DownloadLink link, final Account account) throws Exception {
        String dllink = link.getPluginPatternMatcher();
        String datanode = link.getHost();
        // login
        synchronized (account) {
            login(datanode, account, dllink);
        }
        // download
        dl = jd.plugins.BrowserAdapter.openDownload(br, link, Encoding.htmlDecode(dllink), true, 0);
        dl.startDownload();
    }

    private void login(String datanode, Account account, String dllink) throws Exception {
        String[] parts = account.getUser().split("/");
        String username = Encoding.urlEncode(parts[parts.length - 1]);
        String pass = Encoding.urlEncode(account.getPass());
        String orp = "https://" + datanode + "/esg-orp/j_spring_openid_security_check.htm";
        String idpserver = getDomainName(account.getUser());
        String idp = "https://" + idpserver + "/esgf-idp/idp/login_ids.htm";
        // start login, request file in order to be redirected to orp
        UrlQuery queryinfo = UrlQuery.parse("");
        br.setFollowRedirects(true);
        br.getPage(dllink);
        // post idp form
        queryinfo = UrlQuery.parse("openid_identifier=https://" + idpserver + "/esgf-idp/openid/");
        br.setHeader("Referer", "https://" + datanode + "/");
        br.postPage(orp, queryinfo);
        // post username and password
        queryinfo = UrlQuery.parse("username=" + username + "&password=" + pass);
        br.setHeader("Referer", idp);
        br.setFollowRedirects(false);
        br.postPage(idp, queryinfo);
        // finish login
        br.followRedirect();
        br.followRedirect();
        // Save cookies
        final HashMap<String, Cookies> cookies = br.getCookies();
        account.setProperty("name", Encoding.urlEncode(account.getUser()));
        account.setProperty("pass", Encoding.urlEncode(account.getPass()));
        account.setProperty("cookies", cookies);
    }

    private String getDomainName(String url) throws URISyntaxException {
        URI uri = new URI(url);
        String domain = uri.getHost();
        return domain.startsWith("www.") ? domain.substring(4) : domain;
    }
}