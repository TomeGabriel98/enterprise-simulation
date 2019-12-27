<template>
  <div>

    <!--Stats cards-->
    <div class="row">
      <div class="col-md-6 col-xl-3" v-for="stats in statsCards" :key="stats.title">
        <stats-card>
          <div class="icon-big text-center" :class="`icon-${stats.type}`" slot="header">
            <i :class="stats.icon"></i>
          </div>
          <div class="numbers" slot="content">
            <p>{{stats.title}}</p>
            {{stats.value}}
          </div>
          <div class="stats" slot="footer">
            <i :class="stats.footerIcon"></i> {{stats.footerText}}
          </div>
        </stats-card>
      </div>
    </div>

    <div class="row">
      <div class="col-sm-6">
        <div class="card">
          <div class="card-body">
            <h3 class="card-title"> Produto mensal</h3>
            <p> Transações ao longo do mês </p>

            <div class="container">
            <div class="row">
              <div class="col-sm">
              </div>
              <div class="col-sm">
                <h4> Vendas </h4> 
                <span> (un./valor) </span>
              </div>
              <div class="col-sm">
                <h4> Compras </h4> 
                <span> (un./valor) </span>
              </div>
            </div>
          </div>
          <br>
            <div class="container">
              <div class="row">
                <div class="col-sm">
                  <h5> Smartphone A </h5> 
                  <h5> Smartphone A </h5> 
                  <h5> Smartphone A </h5> 
                </div>
                <div class="col-sm">
                  <p> 15 un. / R$8.001 </p> 
                  <p> 15 un. / R$8.001 </p> 
                  <p> 15 un. / R$8.001 </p> 
                </div>
                <div class="col-sm">
                  <p> 20 un. / R$8.000 </p> 
                  <p> 20 un. / R$8.000 </p> 
                  <p> 20 un. / R$8.000 </p> 
                </div>
              </div>
            </div>
        </div>
        </div>
      </div>
      <div class="col-sm-6">
        <div class="card">
          <div class="card-body">
            <h3 class="card-title"> Produto total </h3>
            <p> Quantidade total de vendas por produto </p>
            <div class="container">
              <div class="row">
                <div class="col-sm">
                  <h3> 325 </h3> 
                  <h5> Smartphone A </h5> 
                </div>
                <div class="col-sm">
                  <h3> 325 </h3> 
                  <h5> Smartphone A </h5> 
                </div>
                <div class="col-sm">
                  <h3> 325 </h3> 
                  <h5> Smartphone A </h5> 
                </div>
              </div>
            </div>
          </div>
        </div>
      <div class="row">
        <div class="col-sm-12">
          <div class="card">
            <div class="card-body">
              <h3 class="card-title"> Estoque atual </h3>
              <div class="container">
                <div class="row">
                  <div class="col-sm">
                    <h3> 325 </h3> 
                    <h5> Smartphone A </h5> 
                  </div>
                  <div class="col-sm">
                    <h3> 325 </h3> 
                    <h5> Smartphone A </h5> 
                  </div>
                  <div class="col-sm">
                    <h3> 325 </h3> 
                    <h5> Smartphone A </h5> 
                  </div>
                </div>
              </div>
            </div>
         </div>
        </div>
      </div>
    </div>
  </div>

  </div>
</template>
<script>
import { StatsCard, ChartCard } from "@/components/index";
import Chartist from 'chartist';
export default {
  components: {
    StatsCard,
    ChartCard
  },
  /**
   * Chart data used to render stats, charts. Should be replaced with server data
   */
  data() {
    return {
      statsCards: [
        {
          type: "warning",
          icon: "ti-wallet",
          title: "Faturamento",
          value: "1.500.000",
          footerText: "Updated now",
          footerIcon: "ti-reload"
        },
        {
          type: "success",
          icon: "ti-wallet",
          title: "Caixa",
          value: "1.500.000",
          footerText: "Last day",
          footerIcon: "ti-calendar"
        },
        {
          type: "danger",
          icon: "ti-wallet",
          title: "Custos fixos",
          value: "1.500.000",
          footerText: "In the last hour",
          footerIcon: "ti-timer"
        },
        {
          type: "info",
          icon: "ti-wallet",
          title: "Emprestimos",
          value: "1.500.000",
          footerText: "Updated now",
          footerIcon: "ti-reload"
        }
      ],
      usersChart: {
        data: {
          labels: [
            "9:00AM",
            "12:00AM",
            "3:00PM",
            "6:00PM",
            "9:00PM",
            "12:00PM",
            "3:00AM",
            "6:00AM"
          ],
          series: [
            [287, 385, 490, 562, 594, 626, 698, 895, 952],
            [67, 152, 193, 240, 387, 435, 535, 642, 744],
            [23, 113, 67, 108, 190, 239, 307, 410, 410]
          ]
        },
        options: {
          low: 0,
          high: 1000,
          showArea: true,
          height: "245px",
          axisX: {
            showGrid: false
          },
          lineSmooth: Chartist.Interpolation.simple({
            divisor: 3
          }),
          showLine: true,
          showPoint: false
        }
      },
      activityChart: {
        data: {
          labels: [
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "Mai",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
          ],
          series: [
            [542, 543, 520, 680, 653, 753, 326, 434, 568, 610, 756, 895],
            [230, 293, 380, 480, 503, 553, 600, 664, 698, 710, 736, 795]
          ]
        },
        options: {
          seriesBarDistance: 10,
          axisX: {
            showGrid: false
          },
          height: "245px"
        }
      },
      preferencesChart: {
        data: {
          labels: ["62%", "32%", "6%"],
          series: [62, 32, 6]
        },
        options: {}
      }
    };
  }
};
</script>
<style>
</style>
